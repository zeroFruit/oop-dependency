package org.zerofruit.ecommerce.service.order;

import org.springframework.stereotype.Service;
import org.zerofruit.ecommerce.domain.order.Order;
import org.zerofruit.ecommerce.domain.order.OrderRepository;
import org.zerofruit.ecommerce.domain.shipping.Shipping;
import org.zerofruit.ecommerce.domain.shipping.ShippingRepository;

import javax.transaction.Transactional;

@Service
public class OrderService {
    private OrderMapper orderMapper;
    private OrderRepository orderRepository;
    private ShippingRepository shippingRepository;

    public OrderService(OrderMapper orderMapper,
                        OrderRepository orderRepository,
                        ShippingRepository shippingRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.shippingRepository = shippingRepository;
    }

    @Transactional
    public void placeOrder(Cart cart) {
        Order order = orderMapper.mapFrom(cart);
        order.place();
        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(IllegalArgumentException::new);
        order.payed();

        Shipping shipping = Shipping.started(order);
        shippingRepository.save(shipping);
    }

    @Transactional
    public void shippingOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(IllegalArgumentException::new);
        order.delivered();

        Shipping shipping = shippingRepository.findById(orderId)
                .orElseThrow(IllegalArgumentException::new);
        shipping.complete();
    }
}
