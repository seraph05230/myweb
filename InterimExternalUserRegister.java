package com.tingweichen.applicationsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InterimExternalUserRegister {

    @NotNull
    private Integer userId;

    @NotBlank
    private String interimPassword;
}
