package com.mcbproperty.authservice.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Support the creation of a new user account with a minimum set of required data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserAccountDTO implements Serializable {

    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String gender;

}
