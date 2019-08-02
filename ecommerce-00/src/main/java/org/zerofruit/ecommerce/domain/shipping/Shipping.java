package org.zerofruit.ecommerce.domain.shipping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.zerofruit.ecommerce.domain.order.Order;

import javax.persistence.*;

@Entity
@Table(name="SHIPPINGS")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Shipping {
    enum ShippingStatus { DELIVERING, DELIVERED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SHIPPING_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name="ORDER_ID")
    @NonNull
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    @NonNull
    private ShippingStatus deliveryStatus;

    public static Shipping started(Order order) {
        return new Shipping(order, ShippingStatus.DELIVERING);
    }

    public void complete() {
        this.deliveryStatus = ShippingStatus.DELIVERED;
    }
}
