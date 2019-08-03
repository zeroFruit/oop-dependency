package org.zerofruit.ecommerce.billing.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zerofruit.ecommerce.order.domain.OrderDeliveredEvent;
import org.zerofruit.ecommerce.store.domain.Store;
import org.zerofruit.ecommerce.store.domain.StoreRepository;

@Component
public class BillShopWithOrderDeliveredEventHandler {
    private static Logger LOG = LoggerFactory.getLogger(BillShopWithOrderDeliveredEventHandler.class);

    private StoreRepository storeRepository;
    private BillingRepository billingRepository;

    public BillShopWithOrderDeliveredEventHandler(
            StoreRepository storeRepository,
            BillingRepository billingRepository
    ) {
        this.storeRepository = storeRepository;
        this.billingRepository = billingRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderDeliveredEvent event) {
        Store store = storeRepository.findById(event.getStoreId())
                .orElseThrow(IllegalArgumentException::new);
        Billing billing = billingRepository.findByStoreId(event.getStoreId())
                .orElse(new Billing(event.getStoreId()));

        billing.billCommissionFee(event.getTotalPrice());

        LOG.info("OrderDeliveredEvent handled");
    }
}
