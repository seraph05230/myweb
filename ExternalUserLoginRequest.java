package com.tingweichen.applicationsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExternalUserLoginRequest {

    @NotBlank
    private String account;

    @NotBlank
    private String password;
}
