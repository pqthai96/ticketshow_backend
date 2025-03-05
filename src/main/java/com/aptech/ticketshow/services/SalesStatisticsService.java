package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.SalesStatisticsDTO;

import java.time.LocalDate;
import java.util.Map;

public interface SalesStatisticsService {

    SalesStatisticsDTO getSalesStatistics(String timeFrame, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getSalesByType(String timeFrame);

    Map<String, Object> getTopEventsBySales(int limit, String timeFrame);
}
