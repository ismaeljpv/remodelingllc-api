package com.remodelingllc.api.util;

import org.springframework.http.MediaType;

public class ContentTypeHelper {

    public static MediaType getMediaType(final String contentType) {
        switch (contentType) {
            case "image/png":
                return MediaType.IMAGE_PNG;
            case "image/jpeg":
            case "image/jpg":
                return MediaType.IMAGE_JPEG;
            default:
                throw new UnsupportedOperationException("Invalid Content Type");
        }
    }

}
