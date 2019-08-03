package org.zerofruit.ecommerce.order.service;

import org.springframework.stereotype.Service;
import org.zerofruit.ecommerce.order.domain.Order;
import org.zerofruit.ecommerce.order.domain.OrderRepository;
import org.zerofruit.ecommerce.order.domain.OrderValidator;

import javax.transaction.Transactional;

@Service
public class OrderService {
    private OrderMapper orderMapper;
    private OrderValidator orderValidator;
    private OrderRepository orderRepository;

    public OrderService(OrderMapper orderMapper,
                        OrderValidator orderValidator,
                        OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderValidator = orderValidator;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void placeOrder(Cart cart) {
        Order order = orderMapper.mapFrom(cart);
        order.place(orderValidator);
        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.payed();
        orderRepository.save(order);
    }

    @Transactional
    public void shippingOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.delivered();
        orderRepository.save(order);
    }
}
