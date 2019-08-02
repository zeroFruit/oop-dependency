package org.zerofruit.ecommerce.domain.store;

import lombok.*;
import org.zerofruit.ecommerce.domain.order.OrderOption;
import org.zerofruit.ecommerce.domain.order.OrderOptionGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "OPTION_GROUP_SPECS")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class OptionGroupSpecification {
    @Id
    @GeneratedValue
    @Column(name="OPTION_GROUP_SPEC_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name="EXCLUSIVE")
    private boolean exclusive;

    @Column(name="BASIC")
    private boolean basic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="OPTION_GROUP_SPEC_ID")
    private List<OptionSpecification> optionSpecs = new ArrayList<>();

    public OptionGroupSpecification(String name, List<OptionSpecification> optionSpecs) {
        this.name = name;
        this.optionSpecs = optionSpecs;
    }

    public static OptionGroupSpecification basic(String name, boolean exclusive, OptionSpecification... optionSpecs) {
        return OptionGroupSpecification
                .builder()
                .name(name)
                .exclusive(exclusive)
                .basic(true)
                .optionSpecs(Arrays.asList(optionSpecs))
                .build();
    }

    public static OptionGroupSpecification additive(String name, boolean exclusive, OptionSpecification... optionSpecs) {
        return OptionGroupSpecification
                .builder()
                .name(name)
                .exclusive(exclusive)
                .basic(false)
                .optionSpecs(Arrays.asList(optionSpecs))
                .build();
    }

    public boolean isSatisfiedBy(OrderOptionGroup group) {
        return !isSatisfied(group.getName(), satisfied(group.getOrderOptions()));
    }

    private boolean isSatisfied(String groupName, List<OrderOption> satisfied) {
        if (!name.equals(groupName)) {
            return false;
        }

        if (satisfied.isEmpty()) {
            return false;
        }

        if (exclusive && satisfied.size() > 1) {
            return false;
        }

        return true;
    }

    private List<OrderOption> satisfied(List<OrderOption> options) {
        return this.optionSpecs
                .stream()
                .flatMap(spec -> options.stream().filter(spec::isSatisfiedBy))
                .collect(toList());
    }
}
