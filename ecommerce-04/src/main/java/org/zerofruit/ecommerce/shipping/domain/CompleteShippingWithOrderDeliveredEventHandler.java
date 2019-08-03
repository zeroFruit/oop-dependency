package org.zerofruit.ecommerce.shipping.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zerofruit.ecommerce.order.domain.OrderDeliveredEvent;

@Component
public class CompleteShippingWithOrderDeliveredEventHandler {
    private static Logger LOG = LoggerFactory.getLogger(CompleteShippingWithOrderDeliveredEventHandler.class);

    private ShippingRepository shippingRepository;

    public CompleteShippingWithOrderDeliveredEventHandler(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderDeliveredEvent event) {
        Shipping shipping = shippingRepository.findById(event.getOrderId())
                .orElseThrow(IllegalArgumentException::new);
        shipping.complete();

        LOG.info("OrderDeliveredEvent handled");
    }
}
