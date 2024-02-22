package com.mcbproperty.authservice.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type;
    private String username;
    private String userId;
}
