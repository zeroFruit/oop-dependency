package org.zerofruit.ecommerce.domain.store;

import lombok.*;

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

    public static OptionGroupSpecification basic(String name, boolean exclusive, OptionSpecification... options) {
        return OptionGroupSpecification
                .builder()
                .name(name)
                .exclusive(exclusive)
                .basic(true)
                .optionSpecs(Arrays.asList(options))
                .build();
    }

    public static OptionGroupSpecification additive(String name, boolean exclusive, OptionSpecification... options) {
        return OptionGroupSpecification
                .builder()
                .name(name)
                .exclusive(exclusive)
                .basic(false)
                .optionSpecs(Arrays.asList(options))
                .build();
    }

    public boolean isSatisfiedBy(OptionGroup optionGroup) {
        return !isSatisfied(optionGroup.getName(), satisfied(optionGroup.getOptions()));
    }

    private boolean isSatisfied(String groupName, List<Option> satisfied) {
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

    private List<Option> satisfied(List<Option> options) {
        return optionSpecs
                .stream()
                .flatMap(spec -> options.stream().filter(spec::isSatisfiedBy))
                .collect(toList());
    }
}
