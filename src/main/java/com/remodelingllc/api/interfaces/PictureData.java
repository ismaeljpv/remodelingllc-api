package com.remodelingllc.api.interfaces;

import com.remodelingllc.api.entity.enums.MediaType;

public interface PictureData {

    byte[] getPicture();
    String getPictureExtension();
    MediaType getType();

}
