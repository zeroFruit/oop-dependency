package org.zerofruit.ecommerce.order.domain;

import lombok.*;
import org.zerofruit.ecommerce.generic.money.domain.Money;
import org.zerofruit.ecommerce.store.domain.OptionGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ORDER_ITEMS")
@Getter
@NoArgsConstructor
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRODUCT_COUNT")
    private int count;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ITEM_ID")
    private List<OrderOptionGroup> groups = new ArrayList<>();

    @Builder
    public OrderItem(Long id,
                     Long productId,
                     String name,
                     int count,
                     List<OrderOptionGroup> groups) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.count = count;
        this.groups.addAll(groups);
    }

    public Money calculatePrice() {
        return Money.sum(groups, OrderOptionGroup::calculatePrice).times(count);
    }

    private List<OptionGroup> convertToOptionGroups() {
        return groups.stream().map(OrderOptionGroup::convertToOptionGroup).collect(toList());
    }
}
