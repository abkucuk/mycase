package com.getircase.backend.service.impl;

import com.getircase.backend.domain.Order;
import com.getircase.backend.repository.OrderRepository;
import com.getircase.backend.service.BookService;
import com.getircase.backend.service.OrderService;
import com.getircase.backend.service.dto.BookDTO;
import com.getircase.backend.service.dto.OrderDTO;
import com.getircase.backend.service.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final BookService bookService;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, BookService bookService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        orderDTO.getBooks().forEach(bookDTO -> {
            BookDTO updatedBookDto = bookService.findOne(bookDTO.getId()).orElseThrow();
            int stockCount = updatedBookDto.getStockCount();
            updatedBookDto.setStockCount(stockCount - 1);
            bookService.update(updatedBookDto);
        });
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        log.debug("Request to update Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);

                return existingOrder;
            })
            .map(orderRepository::save)
            .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    public Page<OrderDTO> findAllWithEagerRelationships(Pageable pageable) {
        return orderRepository.findAllWithEagerRelationships(pageable).map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findOneWithEagerRelationships(id).map(orderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    @Override
    public Optional<List<OrderDTO>> getOrdersByCustomerId(Long id) {
        log.debug("Request to getOrdersByCustomerId : {}", id);
        List<Order> orderListByCustomerId = orderRepository.findAllByCustomer_Id(id);
        return Optional.of(orderMapper.toDto(orderListByCustomerId));
    }

    @Override
    public List<OrderDTO> findByOrderDatesMounth(String startDate, String endDate) {
        log.debug("Request to getOrdersByCustomerId : {}", startDate + endDate);
        List<Order> orderList = orderRepository.findByOrderDatesMounth(startDate, endDate );
        return orderMapper.toDto(orderList);
    }
}
