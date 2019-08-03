package org.zerofruit.ecommerce.generic.money.infra;

import org.zerofruit.ecommerce.generic.money.domain.Ratio;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatioConverter implements AttributeConverter<Ratio, Double> {
    @Override
    public Double convertToDatabaseColumn(Ratio ratio) {
        return ratio.getRate();
    }

    @Override
    public Ratio convertToEntityAttribute(Double rate) {
        return Ratio.valueOf(rate);
    }
}
