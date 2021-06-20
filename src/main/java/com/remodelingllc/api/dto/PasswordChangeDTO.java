package com.remodelingllc.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PasswordChangeDTO {

    @NotNull(message = "Username cant be null")
    private String username;
    @NotNull(message = "Password cant be null")
    private String password;
    @NotNull(message = "Token cant be null")
    private String token;

}
