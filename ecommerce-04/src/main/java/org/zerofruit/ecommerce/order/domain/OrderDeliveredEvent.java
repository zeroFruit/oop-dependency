package org.zerofruit.ecommerce.order.domain;

import org.zerofruit.ecommerce.generic.money.domain.Money;

public class OrderDeliveredEvent {
    private Order order;

    public OrderDeliveredEvent(Order order) {
        this.order = order;
    }

    public Long getOrderId() {
        return order.getId();
    }

    public Long getStoreId() {
        return order.getStoreId();
    }

    public Money getTotalPrice() {
        return order.calculateTotalPrice();
    }
}
