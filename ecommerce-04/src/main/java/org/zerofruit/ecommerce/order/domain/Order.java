package org.zerofruit.ecommerce.order.domain;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.zerofruit.ecommerce.generic.money.domain.Money;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Order extends AbstractAggregateRoot<Order> {
    private static Logger LOG = LoggerFactory.getLogger(Order.class);

    public enum OrderStatus { ORDERED, PAYED, DELIVERED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "STORE_ID")
    private Long storeId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private List<OrderItem> orderItems;

    @Column(name="ORDERED_TIME")
    private LocalDateTime orderedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus orderStatus;

    public void place(OrderValidator orderValidator) {
        orderValidator.validate(this);
        ordered();
    }

    public List<Long> getProductIds() {
        return orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toList());
    }

    private void ordered() {
        this.orderStatus = OrderStatus.ORDERED;
    }

    public void payed() {
        this.orderStatus = OrderStatus.PAYED;
        registerEvent(new OrderPayedEvent(this));
        LOG.info("OrderPayedEvent registered");
    }

    public void delivered() {
        this.orderStatus = OrderStatus.DELIVERED;
        registerEvent(new OrderDeliveredEvent(this));
        LOG.info("OrderDeliveredEvent registered");
    }

    public Money calculateTotalPrice() {
        return Money.sum(orderItems, OrderItem::calculatePrice);
    }

}
