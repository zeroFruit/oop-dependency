package org.zerofruit.ecommerce.generic.money.infra;

import org.zerofruit.ecommerce.generic.money.domain.Money;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {
    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money.getAmount().longValue();
    }

    @Override
    public Money convertToEntityAttribute(Long amount) {
        return Money.wons(amount);
    }
}

