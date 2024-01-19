package com.tingweichen.applicationsystem.model;

import com.tingweichen.applicationsystem.constant.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ExternalUser {

    private Integer userId;

    private String account;

    private String password;

    private String userUid;

    private String sbNumber;

    private AccountStatus accountStatus;

    private Date createdDate;

    private Date lastLoginDate;

    private Date passwordLastModifiedDate;

    private Integer errorCount;
}
