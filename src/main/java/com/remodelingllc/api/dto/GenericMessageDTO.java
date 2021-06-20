package com.remodelingllc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class GenericMessageDTO {

    private String message;
    private boolean success;

}
