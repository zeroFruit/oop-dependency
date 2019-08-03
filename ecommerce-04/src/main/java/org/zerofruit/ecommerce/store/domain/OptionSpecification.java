package org.zerofruit.ecommerce.store.domain;

import lombok.*;
import org.zerofruit.ecommerce.generic.money.domain.Money;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="OPTION_SPECS")
@Getter
@NoArgsConstructor
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

    @Builder
    public OptionSpecification(Long id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public OptionSpecification(String name, Money price) {
        this(null, name, price);
    }

    public boolean isSatisfiedBy(Option option) {
        return Objects.equals(name, option.getName()) && Objects.equals(price, option.getPrice());
    }
}
