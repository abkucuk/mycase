package com.getircase.backend.service;

import com.getircase.backend.service.dto.OrderDTO;

import java.util.List;

public interface StatisticService {
    List<OrderDTO> getOrdersByMounth(String startDate, String endDate);

    Float getTotalOrderCount ();

    Float amountOfAllPurchasedOrders();


}
