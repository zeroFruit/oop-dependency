package org.zerofruit.ecommerce.billing.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerofruit.ecommerce.generic.money.domain.Money;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "BILLINGS")
@Getter
@NoArgsConstructor
public class Billing {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="BILLING_ID")
    private Long id;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "COMMISSION")
    private Money commission = Money.ZERO;

    @Builder
    public Billing(Long id, Long storeId, Money commission) {
        this.id = id;
        this.storeId = storeId;
        this.commission = commission;
    }

    public Billing(Long storeId) {
        this(null, storeId, Money.ZERO);
    }

    public void billCommissionFee(Money commission) {
        commission = commission.plus(commission);
    }
}
