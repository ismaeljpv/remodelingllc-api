package com.remodelingllc.api.entity.converter;

import com.remodelingllc.api.entity.enums.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(final Status status) {
        return status.getStatus();
    }

    @Override
    public Status convertToEntityAttribute(final String value) {
        return Stream.of(Status.values())
                .filter(s -> s.getStatus().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
