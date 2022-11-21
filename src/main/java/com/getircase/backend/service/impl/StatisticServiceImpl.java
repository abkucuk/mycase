package com.getircase.backend.service.impl;

import com.getircase.backend.service.OrderService;
import com.getircase.backend.service.StatisticService;
import com.getircase.backend.service.dto.OrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {
    private final OrderService orderService;

    public StatisticServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public List<OrderDTO> getOrdersByMounth(String startDate, String endDate) {
        return orderService.findByOrderDatesMounth(startDate, endDate);
    }

    @Override
    public Float getTotalOrderCount() {
        return null;
    }

    @Override
    public Float amountOfAllPurchasedOrders() {
        return null;
    }
}
