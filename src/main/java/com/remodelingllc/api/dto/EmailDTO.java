package com.remodelingllc.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailDTO {

    private String to;
    private String token;
    private String username;
    private String name;
    @NotNull(message = "Subject cant be null")
    private String subject;
    @NotNull(message = "Email cant be null")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$", message = "Email not valid")
    private String from;
    @NotNull(message = "Message cant be null")
    private String message;
    private String phoneNumber;

}
