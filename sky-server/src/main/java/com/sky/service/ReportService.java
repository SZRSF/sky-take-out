package com.sky.service;


import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

/**
 * 报表
 *
 * @author zengzhicheng
 */
public interface ReportService {

    /**
     * 根据时间区间统计营业额
     * @param beginTime
     * @param endTime
     * @return
     */
    TurnoverReportVO getTurnover(LocalDate beginTime, LocalDate endTime);
}
