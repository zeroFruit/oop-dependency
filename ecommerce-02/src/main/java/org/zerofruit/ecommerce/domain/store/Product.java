package org.zerofruit.ecommerce.domain.store;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name="PRODUCT_NAME")
    private String name;

    @Column(name="PRODUCT_DESCRIPTION")
    private String description;

    @Column(name="STORE_ID")
    private Long storeId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_ID")
    private List<OptionGroupSpecification> optionGroupSpecs = new ArrayList<>();

    public Money getBasePrice() {
        return getBasicOptionGroupSpecs().getOptionSpecs().get(0).getPrice();
    }

    private OptionGroupSpecification getBasicOptionGroupSpecs() {
        return optionGroupSpecs
                .stream()
                .filter(OptionGroupSpecification::isBasic)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public void validateOrder(String orderName, List<OptionGroup> optionGroups){
        if (!this.name.equals(orderName)) {
            throw new IllegalArgumentException("product name is changed");
        }

        if (!isSatisfiedBy(optionGroups)) {
            throw new IllegalStateException("product has changed");
        }
    }

    private boolean isSatisfiedBy(List<OptionGroup> cartOptionGroups) {
        return cartOptionGroups.stream().anyMatch(this::isSatisfiedBy);
    }

    private boolean isSatisfiedBy(OptionGroup group) {
        return optionGroupSpecs
                .stream()
                .anyMatch(spec -> spec.isSatisfiedBy(group));
    }
}
