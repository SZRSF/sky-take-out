package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单业务接口实现
 *
 * @author zengzhicheng
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    /**
     * 用户下单业务实现
     *
     * @param ordersSubmitDTO 下单的信息
     * @return 返回下单成功的订单的信息
     */
    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        // 1.处理异常情况（地址为空，超出配送距离，购物车为空）
        // 1.1 获取用户下单的地址
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        // 1.2 判断地址是否为空
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        // 2.判断当前用户购物车是否为空
        // 2.1获得当前用户的购物车数据
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        // 2.2判断购物车数据是否为空
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 3.构造订单数据
        Orders order = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, order);
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        order.setConsignee(addressBook.getConsignee());
        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setUserId(userId);
        order.setStatus(Orders.PENDING_PAYMENT);
        order.setPayStatus(Orders.UN_PAID);
        order.setOrderTime(LocalDateTime.now());

        // 4.向订单表插入1条数据
        orderMapper.insert(order);

        // 5.订单明细数据
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetailList.add(orderDetail);
        }

        // 6.向明细表插入n条数据
        orderDetailMapper.insertBatch(orderDetailList);

        // 7.清空购物车中的数据
        shoppingCartMapper.deleteByUserId(userId);

        // 8.封装返回的结果
        return OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime())
                .build();
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 订单支付信息
     * @return 返回
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
       /* // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                //商户订单号
                ordersPaymentDTO.getOrderNumber(),
                //支付金额，单位 元
                new BigDecimal("0.01"),
                //商品描述
                "苍穹外卖订单",
                //微信用户的openid
                user.getOpenid()
        );

        if (jsonObject.getString("code") != null && "ORDERPAID".equals(jsonObject.getString("code"))) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;*/

        // 用户确认支付状态之后--更新订单数据
        log.info(ordersPaymentDTO.getOrderNumber());
        this.paySuccess(ordersPaymentDTO.getOrderNumber());
        return new OrderPaymentVO();
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo 订单号
     */
    @Override
    public void paySuccess(String outTradeNo) {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单号查询当前用户的订单
        Orders ordersDb = orderMapper.getByNumberAndUserId(outTradeNo, userId);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDb.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * 查看历史订单
     *
     * @param ordersPageQueryDTO 请求参数
     * @return 返回拼接订单结果
     */
    @Override
    public PageResult historyPage(OrdersPageQueryDTO ordersPageQueryDTO) {
        // 1.分页条件查询历史订单
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
        Page<Orders> page = orderMapper.queryList(ordersPageQueryDTO);

        // 2.查询每个订单的详情
        List<OrderVO> list = new ArrayList();
        // 2.1查询出订单明细，并封装入OrderVO进行响应
        if (page != null && page.getTotal() > 0) {
            for (Orders orders : page) {
                Long orderId = orders.getId();

                // 2.2查询订单明细
                List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

                // 2.3封装数据
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetails);

                // 2.4将每次封装的数据加入到列表中
                list.add(orderVO);
            }
        }
        // 3.拼接数据返回结果
        return new PageResult(page.getTotal(), list);
    }

    /**
     * 查看订单详情
     *
     * @param id 订单id
     * @return 返回查询结果
     */
    @Override
    public OrderVO details(Long id) {
        // 1.根据id查询订单
        Orders orders = orderMapper.getById(id);

        // 2.查询该订单对应的菜品、套餐
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        // 3.封装返回数据
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailList);

        // 4.返回
        return orderVO;
    }

    /**
     * 用户取消订单
     *
     * @param id 订单id
     */
    @Override
    public void userCancelById(Long id) {
        // 1.根据id查询订单
        Orders ordersDb = orderMapper.getById(id);

        // 2.判断订单是否存在
        if (ordersDb == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        /* 3.判断订单状态 只有待支付和待接单能直接取消订单，其他需要与商家对接
            （1待付款 2待接单 3已接单 4派送中 5已完成 6已取消）*/
        if (ordersDb.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersDb.getId());

        // 3.支付状态修改为 退款
        orders.setPayStatus(Orders.REFUND);

        // 4.更新订单
        orders.setCancelReason("用户取消");
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * 再来一单业务实现
     *
     * @param id 订单id
     */
    @Override
    public void repetition(Long id) {
        // 1.获取当前用户id
        Long userId = BaseContext.getCurrentId();

        //2.根据订单id查询当前订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);

        // 3.将订单详情对象变为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();

            // 3.1将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        // 4.将购物车对象批量添加到数据库
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    /**
     * 订单搜索 业务实现
     *
     * @param ordersPageQueryDTO 订单搜索参数
     * @return 返回搜索结果
     */
    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        // 1.设置分页参数
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        // 2.分页条件查询
        Page<Orders> page = orderMapper.queryList(ordersPageQueryDTO);

        // 3.将Orders转化为OrderVO
        List<OrderVO> orderVOList = getOrderVOList(page);

        return new PageResult(page.getTotal(), orderVOList);
    }

    /**
     * 各个状态的订单数量统计
     *
     * @return 返回订单数量统计
     */
    @Override
    public OrderStatisticsVO statistics() {
        // 1.根据状态（订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消）查出各个状态的订单数量
        // 1.1 查询出待接单订单数量
        Integer toBeConfirmed = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        // 1.2 查询出待派送订单数量
        Integer confirmed = orderMapper.countStatus(Orders.CONFIRMED);
        // 1.3 查询出派送中订单数量
        Integer deliveryInProgress = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);

        // 2.将查询出的数据封装到orderStatisticsVO
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        return orderStatisticsVO;
    }

    /**
     * 接单
     *
     * @param ordersConfirmDTO 接单信息
     */
    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        // 1.构建订单数据
        Orders orders = Orders.builder()
                .id(ordersConfirmDTO.getId())
                .status(Orders.CONFIRMED)
                .build();

        // 2.更新订单状态
        orderMapper.update(orders);
    }

    /**
     * 拒单
     *
     * @param ordersRejectionDTO 拒绝订单信息
     */
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
        // 1.根据id查询订单
        Orders ordersDb = orderMapper.getById(ordersRejectionDTO.getId());

        // 2.待接单状态才可以拒单
        if (ordersDb == null || !ordersDb.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 3.更新订单状态、拒单原因、取消时间
        Orders orders = new Orders();
        orders.setId(ordersDb.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());

        // 4.去数据库更新数据
        orderMapper.update(orders);
    }

    /**
     * 取消订单
     *
     * @param ordersCancelDTO 取消订单信息
     */
    @Override
    public void cancel(OrdersCancelDTO ordersCancelDTO) {
            // 1.更新订单状态、取消原因、取消时间
            Orders orders = new Orders();
            orders.setId(ordersCancelDTO.getId());
            orders.setStatus(Orders.CANCELLED);
            orders.setCancelReason(ordersCancelDTO.getCancelReason());
            orders.setCancelTime(LocalDateTime.now());
            orderMapper.update(orders);
    }

    /**
     * 派送订单
     *
     * @param id 订单id
     */
    @Override
    public void delivery(Long id) {
        // 1.根据id查询订单
        Orders ordersDb = orderMapper.getById(id);

        // 2.校验订单是否存在，并且状态为已经接单（3）
        if (ordersDb == null || !ordersDb.getStatus().equals(Orders.CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 3.更新订单状态,状态转为派送中
        Orders orders = new Orders();
        orders.setId(ordersDb.getId());
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);

        // 4.数据库进行更新
        orderMapper.update(orders);
    }

    /**
     * 将Orders转化为OrderVO
     */
    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        // 返回订单菜品信息，自定义OrderVO响应结果
        List<OrderVO> orderVOList = new ArrayList<>();

        List<Orders> ordersList = page.getResult();
        if (!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {
                // 将共同字段复制到OrderVO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                String orderDishes = getOrderDishesStr(orders);

                // 将订单菜品信息封装到orderVO中，并添加到orderVOList
                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    /**
     * 根据订单id获取菜品信息字符串
     *
     * @param orders 订单数据
     * @return 返回拼接的信息
     */
    private String getOrderDishesStr(Orders orders) {
        // 1.查询订单菜品详情信息
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        // 2.将每一条订单菜品信息拼接为字符串（格式：宫保鸡丁*3；）
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            return x.getName() + "*" + x.getNumber() + ";";
        }).collect(Collectors.toList());

        // 3.将该订单对应的所有菜品信息拼接在一起
        return String.join("", orderDishList);
    }

}
