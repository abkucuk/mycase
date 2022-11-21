package com.getircase.backend.web.rest;

import com.getircase.backend.service.StatisticService;
import com.getircase.backend.service.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsResource {
    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticService statisticService;


    public StatisticsResource(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/mounthly-report/{year}")
    public ResponseEntity<List<OrderDTO>> retrieveMounthlyStatistics(@PathVariable String year, @RequestParam Integer mounth){
        String actualMaximum = String.valueOf(Calendar.getInstance().getActualMaximum(mounth));
        String startDate = String.format("1-%s-%s",mounth.toString(),year);
        String endDate = String.format("%s-%s-%s",actualMaximum,mounth.toString(),year);

        var orders = statisticService.getOrdersByMounth(startDate.toString(), endDate.toString());
        return ResponseEntity.ok(orders);
    }
}
