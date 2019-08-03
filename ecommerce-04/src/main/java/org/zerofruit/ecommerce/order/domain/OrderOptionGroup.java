package org.zerofruit.ecommerce.order.domain;

import lombok.*;
import org.zerofruit.ecommerce.generic.money.domain.Money;
import org.zerofruit.ecommerce.store.domain.OptionGroup;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ORDER_OPTION_GROUPS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString
public class OrderOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_OPTION_GROUP_ID")
    private Long id;

    @Column(name="NAME")
    @NonNull
    private String name;

    @ElementCollection
    @CollectionTable(name = "ORDER_OPTIONS", joinColumns = @JoinColumn(name = "ORDER_OPTION_GROUP_ID"))
    @NonNull
    private List<OrderOption> orderOptions;

    public Money calculatePrice() {
        return Money.sum(orderOptions, OrderOption::getPrice);
    }

    public OptionGroup convertToOptionGroup() {
        return OptionGroup
                .builder()
                .name(name)
                .options(
                        orderOptions
                                .stream()
                                .map(OrderOption::convertToOption)
                                .collect(toList())
                )
                .build();
    }
}
