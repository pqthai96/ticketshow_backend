package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.SalesStatisticsDTO;
import com.aptech.ticketshow.data.entities.Order;
import com.aptech.ticketshow.data.entities.OrderItem;
import com.aptech.ticketshow.data.repositories.EventRepository;
import com.aptech.ticketshow.data.repositories.OrderItemRepository;
import com.aptech.ticketshow.data.repositories.OrderRepository;
import com.aptech.ticketshow.services.SalesStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesStatisticsServiceImpl implements SalesStatisticsService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public SalesStatisticsDTO getSalesStatistics(String timeFrame, LocalDate startDate, LocalDate endDate) {
        SalesStatisticsDTO statistics = new SalesStatisticsDTO();

        // Set default date range if not provided
        if (startDate == null || endDate == null) {
            endDate = LocalDate.now();

            switch (timeFrame.toLowerCase()) {
                case "daily":
                    startDate = endDate.minusDays(30); // Last 30 days
                    break;
                case "weekly":
                    startDate = endDate.minusWeeks(12); // Last 12 weeks
                    break;
                case "yearly":
                    startDate = endDate.minusYears(5); // Last 5 years
                    break;
                case "monthly":
                default:
                    startDate = endDate.minusMonths(12); // Last 12 months
                    break;
            }
        }

//        LocalDateTime startDateTime = startDate.atStartOfDay();
        Date startDateTime = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        Date endDateTime = Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        // Fetch all orders within the date range
        List<Order> orders = orderRepository.findAllByOrderDateBetween(startDateTime, endDateTime);

        // Process ticket and seat sales data
        Map<String, BigDecimal> ticketSalesByPeriod = new LinkedHashMap<>();
        Map<String, BigDecimal> seatSalesByPeriod = new LinkedHashMap<>();
        Map<String, Integer> ticketCountByPeriod = new LinkedHashMap<>();
        Map<String, Integer> seatCountByPeriod = new LinkedHashMap<>();

        // Generate time period labels based on timeFrame
        List<String> timePeriods = generateTimePeriods(timeFrame, startDate, endDate);

        // Initialize maps with zero values for all periods
        for (String period : timePeriods) {
            ticketSalesByPeriod.put(period, BigDecimal.ZERO);
            seatSalesByPeriod.put(period, BigDecimal.ZERO);
            ticketCountByPeriod.put(period, 0);
            seatCountByPeriod.put(period, 0);
        }

        BigDecimal totalTicketRevenue = BigDecimal.ZERO;
        BigDecimal totalSeatRevenue = BigDecimal.ZERO;
        int totalTicketsSold = 0;
        int totalSeatsSold = 0;

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

            for (OrderItem item : orderItems) {
                String period = formatPeriod(timeFrame, order.getOrderDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());

                if (item.getTicket() != null) {
                    // Ticket sale
                    BigDecimal currentTicketSales = ticketSalesByPeriod.get(period);
                    BigDecimal itemTotal = BigDecimal.valueOf(item.getQuantity())
                            .multiply(BigDecimal.valueOf(item.getTicket().getPrice()));

                    ticketSalesByPeriod.put(period, currentTicketSales.add(itemTotal));
                    totalTicketRevenue = totalTicketRevenue.add(itemTotal);

                    int currentTicketCount = ticketCountByPeriod.get(period);
                    ticketCountByPeriod.put(period, currentTicketCount + item.getQuantity());
                    totalTicketsSold += item.getQuantity();
                } else if (item.getSeatValue() != null) {
                    // Seat sale
                    BigDecimal currentSeatSales = seatSalesByPeriod.get(period);
                    BigDecimal itemTotal = BigDecimal.valueOf(item.getOrder().getEvent().getSeatPrice());

                    seatSalesByPeriod.put(period, currentSeatSales.add(itemTotal));
                    totalSeatRevenue = totalSeatRevenue.add(itemTotal);

                    int currentSeatCount = seatCountByPeriod.get(period);
                    seatCountByPeriod.put(period, currentSeatCount + 1);
                    totalSeatsSold++;
                }
            }
        }

        // Convert maps to lists for the DTO
        List<SalesStatisticsDTO.TimeSeriesDataPoint> ticketSalesData = ticketSalesByPeriod.entrySet().stream()
                .map(entry -> new SalesStatisticsDTO.TimeSeriesDataPoint(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        List<SalesStatisticsDTO.TimeSeriesDataPoint> seatSalesData = seatSalesByPeriod.entrySet().stream()
                .map(entry -> new SalesStatisticsDTO.TimeSeriesDataPoint(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Calculate sales trend
        Map<String, Object> salesTrend = calculateSalesTrend(ticketSalesByPeriod, seatSalesByPeriod);

        // Set all data in the DTO
        statistics.setTicketSales(ticketSalesData);
        statistics.setSeatSales(seatSalesData);
        statistics.setTotalTicketRevenue(totalTicketRevenue);
        statistics.setTotalSeatRevenue(totalSeatRevenue);
        statistics.setTotalTicketsSold(totalTicketsSold);
        statistics.setTotalSeatsSold(totalSeatsSold);
        statistics.setSalesTrend(salesTrend);

        return statistics;
    }

    @Override
    public Map<String, Object> getSalesByType(String timeFrame) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        switch (timeFrame.toLowerCase()) {
            case "daily":
                startDate = endDate.minusDays(30);
                break;
            case "weekly":
                startDate = endDate.minusWeeks(12);
                break;
            case "yearly":
                startDate = endDate.minusYears(5);
                break;
            case "monthly":
            default:
                startDate = endDate.minusMonths(12);
                break;
        }

        SalesStatisticsDTO statistics = getSalesStatistics(timeFrame, startDate, endDate);

        Map<String, Object> result = new HashMap<>();
        result.put("ticketSales", statistics.getTicketSales());
        result.put("seatSales", statistics.getSeatSales());
        result.put("totalTicketRevenue", statistics.getTotalTicketRevenue());
        result.put("totalSeatRevenue", statistics.getTotalSeatRevenue());
        result.put("totalRevenue", statistics.getTotalTicketRevenue().add(statistics.getTotalSeatRevenue()));

        result.put("totalTicketsSold", statistics.getTotalTicketsSold());
        result.put("totalSeatsSold", statistics.getTotalSeatsSold());

        return result;
    }

    @Override
    public Map<String, Object> getTopEventsBySales(int limit, String timeFrame) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        switch (timeFrame.toLowerCase()) {
            case "daily":
                startDate = endDate.minusDays(30);
                break;
            case "weekly":
                startDate = endDate.minusWeeks(12);
                break;
            case "yearly":
                startDate = endDate.minusYears(5);
                break;
            case "monthly":
            default:
                startDate = endDate.minusMonths(12);
                break;
        }

        Date startDateTime = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDateTime = Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        List<Order> orders = orderRepository.findAllByOrderDateBetween(startDateTime, endDateTime);

        // Group by event and calculate total revenue
        Map<Long, BigDecimal> revenueByEvent = new HashMap<>();
        Map<Long, Integer> ticketsByEvent = new HashMap<>();

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

            for (OrderItem item : orderItems) {
                if (item.getOrder().getEvent().getId() != null) {
                    Long eventId = item.getOrder().getEvent().getId();

                    // Calculate revenue
                    BigDecimal itemRevenue;
                    if (item.getTicket() != null) {
                        itemRevenue = BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(item.getTicket().getPrice()));
                    } else {
                        itemRevenue = BigDecimal.valueOf(item.getOrder().getEvent().getSeatPrice());
                    }

                    // Update revenue
                    revenueByEvent.put(eventId,
                            revenueByEvent.getOrDefault(eventId, BigDecimal.ZERO).add(itemRevenue));

                    // Update ticket count
                    int ticketCount = item.getTicket() != null ? item.getQuantity() : 1;
                    ticketsByEvent.put(eventId,
                            ticketsByEvent.getOrDefault(eventId, 0) + ticketCount);
                }
            }
        }

        // Sort events by revenue
        List<Map.Entry<Long, BigDecimal>> sortedEvents = revenueByEvent.entrySet().stream()
                .sorted(Map.Entry.<Long, BigDecimal>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());

        // Build result
        List<Map<String, Object>> topEventsList = new ArrayList<>();
        for (Map.Entry<Long, BigDecimal> entry : sortedEvents) {
            Long eventId = entry.getKey();
            BigDecimal revenue = entry.getValue();
            Integer ticketCount = ticketsByEvent.get(eventId);

            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventId", eventId);
            eventData.put("eventName", eventRepository.findById(eventId).map(e -> e.getTitle()).orElse("Unknown Event"));
            eventData.put("revenue", revenue);
            eventData.put("ticketsSold", ticketCount);

            topEventsList.add(eventData);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("topEvents", topEventsList);
        result.put("timeFrame", timeFrame);

        return result;
    }

    // Helper methods
    private List<String> generateTimePeriods(String timeFrame, LocalDate startDate, LocalDate endDate) {
        List<String> periods = new ArrayList<>();
        DateTimeFormatter formatter;

        switch (timeFrame.toLowerCase()) {
            case "daily":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    periods.add(date.format(formatter));
                }
                break;

            case "weekly":
                formatter = DateTimeFormatter.ofPattern("yyyy-'W'w");
                WeekFields weekFields = WeekFields.of(Locale.getDefault());

                // Adjust to start of week
                LocalDate weekStart = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                for (LocalDate date = weekStart; !date.isAfter(endDate); date = date.plusWeeks(1)) {
                    int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
                    periods.add(date.getYear() + "-W" + weekNumber);
                }
                break;

            case "yearly":
                formatter = DateTimeFormatter.ofPattern("yyyy");

                // Adjust to start of year
                LocalDate yearStart = startDate.withDayOfYear(1);

                for (LocalDate date = yearStart; !date.isAfter(endDate); date = date.plusYears(1)) {
                    periods.add(date.format(formatter));
                }
                break;

            case "monthly":
            default:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");

                // Adjust to start of month
                LocalDate monthStart = startDate.withDayOfMonth(1);

                for (LocalDate date = monthStart; !date.isAfter(endDate); date = date.plusMonths(1)) {
                    periods.add(date.format(formatter));
                }
                break;
        }

        return periods;
    }

    private String formatPeriod(String timeFrame, LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        switch (timeFrame.toLowerCase()) {
            case "daily":
                return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            case "weekly":
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
                return date.getYear() + "-W" + weekNumber;

            case "yearly":
                return date.format(DateTimeFormatter.ofPattern("yyyy"));

            case "monthly":
            default:
                return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }
    }

    private Map<String, Object> calculateSalesTrend(
            Map<String, BigDecimal> ticketSales,
            Map<String, BigDecimal> seatSales) {

        Map<String, Object> trend = new HashMap<>();

        // Calculate total revenue for each period
        Map<String, BigDecimal> totalRevenue = new LinkedHashMap<>();

        for (String period : ticketSales.keySet()) {
            BigDecimal periodTotal = ticketSales.get(period).add(seatSales.get(period));
            totalRevenue.put(period, periodTotal);
        }

        // Calculate growth rates if we have more than one period
        if (totalRevenue.size() > 1) {
            List<String> periods = new ArrayList<>(totalRevenue.keySet());
            List<BigDecimal> growthRates = new ArrayList<>();

            for (int i = 1; i < periods.size(); i++) {
                BigDecimal current = totalRevenue.get(periods.get(i));
                BigDecimal previous = totalRevenue.get(periods.get(i - 1));

                if (previous.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal growthRate = current.subtract(previous)
                            .divide(previous, 4, BigDecimal.ROUND_HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    growthRates.add(growthRate);
                } else {
                    // If previous is zero, growth rate is undefined
                    growthRates.add(null);
                }
            }

            trend.put("growthRates", growthRates);

            // Calculate average growth rate
            if (!growthRates.isEmpty()) {
                BigDecimal sum = BigDecimal.ZERO;
                int count = 0;

                for (BigDecimal rate : growthRates) {
                    if (rate != null) {
                        sum = sum.add(rate);
                        count++;
                    }
                }

                if (count > 0) {
                    BigDecimal avgGrowthRate = sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
                    trend.put("averageGrowthRate", avgGrowthRate);
                }
            }
        }

        // Calculate proportion of ticket sales vs seat sales
        BigDecimal totalTicketSales = ticketSales.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSeatSales = seatSales.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSales = totalTicketSales.add(totalSeatSales);

        if (totalSales.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ticketPercentage = totalTicketSales
                    .divide(totalSales, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

            BigDecimal seatPercentage = totalSeatSales
                    .divide(totalSales, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

            trend.put("ticketSalesPercentage", ticketPercentage.setScale(2, BigDecimal.ROUND_HALF_UP));
            trend.put("seatSalesPercentage", seatPercentage.setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        return trend;
    }
}
