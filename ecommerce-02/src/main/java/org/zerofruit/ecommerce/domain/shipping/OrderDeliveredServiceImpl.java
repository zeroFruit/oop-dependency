package org.zerofruit.ecommerce.domain.shipping;

import org.springframework.stereotype.Component;
import org.zerofruit.ecommerce.domain.order.Order;
import org.zerofruit.ecommerce.domain.order.OrderDeliveredService;
import org.zerofruit.ecommerce.domain.order.OrderPayedService;
import org.zerofruit.ecommerce.domain.order.OrderRepository;
import org.zerofruit.ecommerce.domain.store.Store;
import org.zerofruit.ecommerce.domain.store.StoreRepository;

@Component
public class OrderDeliveredServiceImpl implements OrderPayedService, OrderDeliveredService {
    private OrderRepository orderRepository;
    private StoreRepository storeRepository;
    private ShippingRepository shippingRepository;

    public OrderDeliveredServiceImpl(
       OrderRepository orderRepository,
       StoreRepository storeRepository,
       ShippingRepository shippingRepository
    ) {
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.shippingRepository = shippingRepository;
    }

    @Override
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.payed();

        Shipping shipping = Shipping.started(orderId);
        shippingRepository.save(shipping);
    }

    @Override
    public void deliverOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        Store store = storeRepository.findById(order.getStoreId()).orElseThrow(IllegalArgumentException::new);
        Shipping shipping = shippingRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);

        order.delivered();
        store.billCommissionFee(order.calculateTotalPrice());
        shipping.complete();
    }
}
