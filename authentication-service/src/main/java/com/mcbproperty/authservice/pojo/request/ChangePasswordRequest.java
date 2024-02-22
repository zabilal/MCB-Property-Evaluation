package com.mcbproperty.authservice.pojo.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Old password cannot be null")
    private String oldPassword;
    @NotBlank(message = "New password cannot be null")
    private String newPassword;
    @NotBlank(message = "Confirm password cannot be null")
    private String confirmPassword;
}
