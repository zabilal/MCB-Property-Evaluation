package com.mcbproperty.authservice.pojo.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
