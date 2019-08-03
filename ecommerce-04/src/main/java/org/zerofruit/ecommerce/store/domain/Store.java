package org.zerofruit.ecommerce.store.domain;

import lombok.*;
import org.zerofruit.ecommerce.generic.money.domain.Money;
import org.zerofruit.ecommerce.generic.money.domain.Ratio;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "STORES")
@Getter
@NoArgsConstructor
@ToString
public class Store {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="STORE_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="OPEN")
    private boolean open;

    @Column(name="MIN_ORDER_AMOUNT")
    private Money minOrderAmount;

    @Column(name="COMMISSION_RATE")
    private Ratio commissionRate;

    @Column(name = "COMMISSION")
    private Money commission = Money.ZERO;

    @Builder
    public Store(Long id,
                 String name,
                 boolean open,
                 Money minOrderAmount,
                 Ratio commissionRate,
                 Money commission) {
        this.id = id;
        this.name = name;
        this.open = open;
        this.minOrderAmount = minOrderAmount;
        this.commissionRate = commissionRate;
        this.commission = commission;
    }

    public boolean isValidOrderAmount(Money amount) {
        return amount.isGreaterThanOrEqual(minOrderAmount);
    }

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = true;
    }

    public void modifyCommissionRate(Ratio commissionRate) {
        this.commissionRate = commissionRate;
    }
}
