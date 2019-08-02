package org.zerofruit.ecommerce.domain.store;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;
import org.zerofruit.ecommerce.domain.generic.money.Ratio;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "STORES")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "STORE_ID")
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
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

    public void billCommissionFee(Money price) {
        commission = commission.plus(commissionRate.of(price));
    }
}
