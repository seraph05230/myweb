package com.tingweichen.applicationsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExternalUserRegisterRequest {

    @NotBlank
    private String account;

    private String password;

    @NotBlank
    private String userUid;

    private String sb_number;


    private String interimPassword;
}
