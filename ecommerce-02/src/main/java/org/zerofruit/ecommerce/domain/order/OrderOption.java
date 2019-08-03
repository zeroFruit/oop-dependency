package org.zerofruit.ecommerce.domain.order;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;
import org.zerofruit.ecommerce.domain.store.Option;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderOption {
    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Money price;

    public Option convertToOption() {
        return Option
                .builder()
                .name(name)
                .price(price)
                .build();
    }
}
