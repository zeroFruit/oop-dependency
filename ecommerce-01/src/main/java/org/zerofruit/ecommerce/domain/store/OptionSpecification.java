package org.zerofruit.ecommerce.domain.store;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;
import org.zerofruit.ecommerce.domain.order.OrderOption;
import org.zerofruit.ecommerce.domain.order.OrderOptionGroup;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="OPTION_SPECS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class OptionSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OPTION_SPEC_ID")
    private Long id;

    @Column(name="NAME")
    @NonNull
    private String name;

    @Column(name="PRICE")
    @NonNull
    private Money price;

    public boolean isSatisfiedBy(OrderOption option) {
        return Objects.equals(name, option.getName()) && Objects.equals(price, option.getPrice());
    }
}
