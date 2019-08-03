package org.zerofruit.ecommerce.service.order;

import org.springframework.stereotype.Service;
import org.zerofruit.ecommerce.domain.order.*;
import org.zerofruit.ecommerce.domain.shipping.Shipping;
import org.zerofruit.ecommerce.domain.shipping.ShippingRepository;

import javax.transaction.Transactional;

@Service
public class OrderService {
    private OrderMapper orderMapper;
    private OrderValidator orderValidator;
    private OrderRepository orderRepository;
    private OrderDeliveredService orderDeliveredService;
    private OrderPayedService orderPayedService;

    public OrderService(OrderMapper orderMapper,
                        OrderValidator orderValidator,
                        OrderRepository orderRepository,
                        OrderPayedService orderPayedService,
                        OrderDeliveredService orderDeliveredService) {
        this.orderMapper = orderMapper;
        this.orderValidator = orderValidator;
        this.orderRepository = orderRepository;
        this.orderPayedService = orderPayedService;
        this.orderDeliveredService = orderDeliveredService;
    }

    @Transactional
    public void placeOrder(Cart cart) {
        Order order = orderMapper.mapFrom(cart);
        order.place(orderValidator);
        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        orderPayedService.payOrder(orderId);
    }

    @Transactional
    public void shippingOrder(Long orderId) {
        orderDeliveredService.deliverOrder(orderId);
    }
}
