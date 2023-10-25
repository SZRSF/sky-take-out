package com.sky.controller.user;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 *
 * @author zengzhicheng
 */
@Slf4j
@Api(tags = "C端-订单接口")
@RestController("userOrderController")
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO 下单的信息
     * @return 返回下单成功的信息
     */
    @ApiOperation("用户下单")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单:{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 订单支付信息
     * @return 返回响应
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception{
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /**
     * 查询历史订单
     *
     * @param ordersPageQueryDTO 查看历史订单参数
     * @return
     */
    @ApiOperation("查看历史订单")
    @GetMapping("/historyOrders")
    public Result<PageResult> historyPage(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("查看历史订单：{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.historyPage(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情
     *
     * @param id 订单Id
     * @return 返回订单数据
     */
    @ApiOperation("查询订单详情")
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        log.info("查询订单详情：{}",id);
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 用户取消订单
     *
     * @param id 订单id
     * @return 返回响应
     */
    @ApiOperation("取消订单")
    @PutMapping("/cancel/{id}")
    public Result cancel(@PathVariable Long id) throws Exception {
        log.info("取消订单：{}", id);
        orderService.userCancelById(id);
        return Result.success();
    }

    /**
     * 再来一单
     *
     * @param id 订单id
     * @return 返回响应
     */
    @ApiOperation("再来一单")
    @PostMapping("/repetition/{id}")
    public Result<String> repetition(@PathVariable Long id) {
        log.info("再来一单：{}", id);
        orderService.repetition(id);
        return Result.success();
    }

    /**
     * 客户催单
     *
     * @param id 订单id
     * @return 返回响应
     */
    @ApiOperation("客户催单")
    @GetMapping("/reminder/{id}")
    public Result<String> reminder(@PathVariable Long id){
        log.info("客户催单：{}", id);
        orderService.reminder(id);
        return Result.success();
    }

}
