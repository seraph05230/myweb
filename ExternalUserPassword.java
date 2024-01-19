package com.tingweichen.applicationsystem.model;

import lombok.Data;

@Data
public class ExternalUserPassword {

    private Integer passwordId;

    private Integer userId;

    private String previousPassword;
}
