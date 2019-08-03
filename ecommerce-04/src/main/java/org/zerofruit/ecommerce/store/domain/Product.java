package org.zerofruit.ecommerce.store.domain;

import lombok.*;
import org.zerofruit.ecommerce.generic.money.domain.Money;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@Getter
@ToString
@NoArgsConstructor
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

    @Builder
    public Product(Long id, Long storeId, String name, String description, OptionGroupSpecification basic, List<OptionGroupSpecification> additives) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.description = description;

        this.optionGroupSpecs.add(basic);
        this.optionGroupSpecs.addAll(additives);
    }

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
