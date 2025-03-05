package com.aptech.ticketshow.data.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SalesStatisticsDTO {
    private List<TimeSeriesDataPoint> ticketSales;
    private List<TimeSeriesDataPoint> seatSales;
    private BigDecimal totalTicketRevenue;
    private BigDecimal totalSeatRevenue;
    private int totalTicketsSold;
    private int totalSeatsSold;
    private Map<String, Object> salesTrend;

    @Setter
    @Getter
    @Data
    @NoArgsConstructor
    public static class TimeSeriesDataPoint {
        // Getters and setters
        private String x;
        private BigDecimal y; // value

        public TimeSeriesDataPoint(String x, BigDecimal y) {
            this.x = x;
            this.y = y;
        }

    }
}