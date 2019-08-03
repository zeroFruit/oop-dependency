package org.zerofruit.ecommerce.domain.shipping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zerofruit.ecommerce.domain.order.OrderPayedEvent;

@Component
public class StartDeliveryWithOrderPayedEventHandler {
    private static Logger LOG = LoggerFactory.getLogger(StartDeliveryWithOrderPayedEventHandler.class);

    private ShippingRepository shippingRepository;

    public StartDeliveryWithOrderPayedEventHandler(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderPayedEvent event) {
        Shipping shipping = shippingRepository.findByOrderId(event.getOrderId())
                .orElse(Shipping.started(event.getOrderId()));

        shippingRepository.save(shipping);

        LOG.info("OrderPayedEvent handled");
    }
}
