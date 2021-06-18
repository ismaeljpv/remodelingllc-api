package com.remodelingllc.api.entity.converter;

import com.remodelingllc.api.entity.enums.MediaType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class MediaTypeConverter implements AttributeConverter<MediaType, String> {

    @Override
    public String convertToDatabaseColumn(final MediaType mediaType) {
        return mediaType.getMediaType();
    }

    @Override
    public MediaType convertToEntityAttribute(final String value) {
        return Stream.of(MediaType.values())
                .filter(m -> m.getMediaType().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
