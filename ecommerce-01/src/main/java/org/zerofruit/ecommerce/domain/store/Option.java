package org.zerofruit.ecommerce.domain.store;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private String name;
    private Money price;
}
