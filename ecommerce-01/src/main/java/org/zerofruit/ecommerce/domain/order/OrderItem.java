package org.zerofruit.ecommerce.domain.order;

import lombok.*;
import org.zerofruit.ecommerce.domain.store.OptionGroup;
import org.zerofruit.ecommerce.domain.store.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ORDER_ITEMS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRODUCT_COUNT")
    private int count;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ITEM_ID")
    private List<OrderOptionGroup> groups = new ArrayList<>();

    public void validate() {
        product.validateOrder(name, convertToOptionGroups());
    }

    private List<OptionGroup> convertToOptionGroups() {
        return groups
                .stream()
                .map(OrderOptionGroup::convertToOptionGroup)
                .collect(toList());
    }


}
