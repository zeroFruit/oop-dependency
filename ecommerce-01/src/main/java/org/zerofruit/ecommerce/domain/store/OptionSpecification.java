package org.zerofruit.ecommerce.domain.store;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="OPTION_SPECS")
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class OptionSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OPTION_SPEC_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="PRICE")
    private Money price;

    public OptionSpecification(String name, Money price) {
        this(null, name, price);
    }

    public boolean isSatisfiedBy(Option option) {
        return Objects.equals(name, option.getName()) && Objects.equals(price, option.getPrice());
    }
}
