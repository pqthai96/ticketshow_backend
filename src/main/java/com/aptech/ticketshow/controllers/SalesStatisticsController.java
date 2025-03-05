package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.SalesStatisticsDTO;
import com.aptech.ticketshow.services.SalesStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class SalesStatisticsController {

    @Autowired
    private SalesStatisticsService salesStatisticsService;

    @GetMapping("/sales/overview")
    public ResponseEntity<SalesStatisticsDTO> getSalesOverview(
            @RequestParam(name = "timeFrame", defaultValue = "monthly") String timeFrame,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        SalesStatisticsDTO statistics = salesStatisticsService.getSalesStatistics(timeFrame, startDate, endDate);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/sales/by-type")
    public ResponseEntity<Map<String, Object>> getSalesByType(
            @RequestParam(name = "timeFrame", defaultValue = "monthly") String timeFrame) {

        Map<String, Object> statistics = salesStatisticsService.getSalesByType(timeFrame);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/top-events")
    public ResponseEntity<Map<String, Object>> getTopEvents(
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "timeFrame", defaultValue = "monthly") String timeFrame) {

        Map<String, Object> topEvents = salesStatisticsService.getTopEventsBySales(limit, timeFrame);
        return ResponseEntity.ok(topEvents);
    }


}
