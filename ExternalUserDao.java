package com.tingweichen.applicationsystem.dao;

import com.tingweichen.applicationsystem.constant.AccountStatus;
import com.tingweichen.applicationsystem.constant.AuthorizationStatus;
import com.tingweichen.applicationsystem.dto.ExternalUserChangePasswordRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserLoginRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserRegisterRequest;
import com.tingweichen.applicationsystem.model.ExternalUser;

public interface ExternalUserDao {

    ExternalUser getExternalUserByAccount(String account);

    ExternalUser getExternalUserById(Integer userId);

    Integer createExternalUser(ExternalUserRegisterRequest externalUserRegisterRequest);

    void updateErrorCount(String account, AuthorizationStatus authorizationStatus);

    void updateAccountStatus(String account, AccountStatus accountStatus);

    void updatePassword(String account, String newHashedPassword);
}
