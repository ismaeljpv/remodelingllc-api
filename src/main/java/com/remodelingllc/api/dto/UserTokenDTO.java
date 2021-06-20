package com.remodelingllc.api.dto;

import com.remodelingllc.api.entity.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTokenDTO {

    private int id;
    private String sub;
    private String username;
    private String firstname;
    private String lastname;
    private Status status;

}
