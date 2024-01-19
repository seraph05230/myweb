package com.tingweichen.applicationsystem.service;

import com.tingweichen.applicationsystem.constant.AccountStatus;
import com.tingweichen.applicationsystem.constant.AuthorizationStatus;
import com.tingweichen.applicationsystem.dto.ExternalUserChangePasswordRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserLoginRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserRegisterRequest;
import com.tingweichen.applicationsystem.dto.InterimExternalUserRegister;
import com.tingweichen.applicationsystem.model.ExternalUser;

public interface ExternalUserService {

    InterimExternalUserRegister register(ExternalUserRegisterRequest externalUserRegisterRequest);

    ExternalUser getExternalUserById(Integer userId);

    ExternalUser getExternalUserByAccount(String account);

    ExternalUser login(ExternalUserLoginRequest externalUserLoginRequest);

    void changePassword(ExternalUserChangePasswordRequest changePasswordRequest);
}
