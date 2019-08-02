package org.zerofruit.ecommerce.domain.store;

import lombok.Builder;
import lombok.Data;
import org.zerofruit.ecommerce.domain.generic.money.Money;

@Data
public class Option {
    private String name;
    private Money price;

    @Builder
    public Option(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
