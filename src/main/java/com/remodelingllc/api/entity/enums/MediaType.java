package com.remodelingllc.api.entity.enums;

public enum MediaType {

    VIDEO("V"), PICTURE("P");
    private final String mediaType;

    MediaType(final String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return mediaType;
    }
}
