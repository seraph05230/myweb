package com.tingweichen.applicationsystem.controller;

import com.tingweichen.applicationsystem.dto.ExternalUserChangePasswordRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserLoginRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserRegisterRequest;
import com.tingweichen.applicationsystem.dto.InterimExternalUserRegister;
import com.tingweichen.applicationsystem.model.ExternalUser;
import com.tingweichen.applicationsystem.service.ExternalUserPasswordService;
import com.tingweichen.applicationsystem.service.ExternalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class ExternalUserController {

    private final static Logger log = LoggerFactory.getLogger(ExternalUserController.class);

    @Autowired
    private ExternalUserService externalUserService;

    @Autowired
    private ExternalUserPasswordService externalUserPasswordService;

    @Transactional
    @PostMapping("/external/users/register")
    public ResponseEntity<String> register(@RequestBody @Valid ExternalUserRegisterRequest externalUserRegisterRequest) {
        InterimExternalUserRegister interimExternalUserRegister = externalUserService.register(externalUserRegisterRequest);

        if (interimExternalUserRegister != null) {
            ExternalUser externalUser = externalUserService.getExternalUserById(interimExternalUserRegister.getUserId());

            // userPasswordId 暫無其他用處
            Integer userPasswordId = externalUserPasswordService.createExternalUserPasswordInfo(externalUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("帳號: " + externalUserRegisterRequest.getAccount() + " 註冊成功！" +
                            "預設密碼: " + interimExternalUserRegister.getInterimPassword() + "，請先登入變更密碼。");
        } else {
            log.warn("帳號: {} 註冊失敗！", externalUserRegisterRequest.getAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/external/users/login")
    public ResponseEntity<?> login(@RequestBody @Valid ExternalUserLoginRequest externalUserLoginRequest) {
        ExternalUser externalUser = externalUserService.login(externalUserLoginRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body("帳號: " + externalUser.getAccount() + " 登入成功！");
    }

    @PostMapping("/external/users/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ExternalUserChangePasswordRequest changePasswordRequest) {

        externalUserService.changePassword(changePasswordRequest);

        return ResponseEntity.status(HttpStatus.OK).body("密碼更新成功");
    }
}
