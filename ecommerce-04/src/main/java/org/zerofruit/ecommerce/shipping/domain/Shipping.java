package org.zerofruit.ecommerce.shipping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

    @JoinColumn(name="ORDER_ID")
    @NonNull
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    @NonNull
    private ShippingStatus deliveryStatus;

    public static Shipping started(Long orderId) {
        return new Shipping(orderId, ShippingStatus.DELIVERING);
    }

    public void complete() {
        this.deliveryStatus = ShippingStatus.DELIVERED;
    }
}
