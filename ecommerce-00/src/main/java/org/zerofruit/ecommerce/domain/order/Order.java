package org.zerofruit.ecommerce.domain.order;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;
import org.zerofruit.ecommerce.domain.store.Store;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Order {
    public enum OrderStatus { ORDERED, PAYED, DELIVERED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private List<OrderItem> orderItems;

    @Column(name="ORDERED_TIME")
    private LocalDateTime orderedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus orderStatus;

    public void place() {
        validate();
        ordered();
    }

    private void validate() {
        if (orderItems.isEmpty()) {
            throw new IllegalStateException("Order items are empty");
        }

        if (!store.isOpen()) {
            throw new IllegalArgumentException("Store is not open");
        }

        if (!store.isValidOrderAmount(calculateTotalPrice())) {
            throw new IllegalArgumentException("Order price is not over minimum order amount");
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.validate();
        }
    }

    private void ordered() {
        this.orderStatus = OrderStatus.ORDERED;
    }

    public void payed() {
        this.orderStatus = OrderStatus.PAYED;
    }

    public void delivered() {
        this.orderStatus = OrderStatus.DELIVERED;
        this.store.billCommissionFee(calculateTotalPrice());
    }

    private Money calculateTotalPrice() {
        return Money.sum(orderItems, OrderItem::calculatePrice);
    }

}
