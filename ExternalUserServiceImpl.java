package com.tingweichen.applicationsystem.service;

import com.tingweichen.applicationsystem.constant.AccountStatus;
import com.tingweichen.applicationsystem.constant.AuthorizationStatus;
import com.tingweichen.applicationsystem.dao.ExternalUserDao;
import com.tingweichen.applicationsystem.dto.ExternalUserChangePasswordRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserLoginRequest;
import com.tingweichen.applicationsystem.dto.ExternalUserRegisterRequest;
import com.tingweichen.applicationsystem.dto.InterimExternalUserRegister;
import com.tingweichen.applicationsystem.model.ExternalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    private final static Logger log = LoggerFactory.getLogger(ExternalUserServiceImpl.class);

    @Autowired
    private ExternalUserDao externalUserDao;

    @Override
    public InterimExternalUserRegister register(ExternalUserRegisterRequest externalUserRegisterRequest) {
        ExternalUser externalUser = externalUserDao.getExternalUserByAccount(externalUserRegisterRequest.getAccount());

        if (externalUser != null) {
            log.warn("帳號: {} 已註冊！", externalUser.getAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String interimPassword = DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis()).getBytes()).substring(0, 8);

        externalUserRegisterRequest.setPassword(DigestUtils.md5DigestAsHex(interimPassword.getBytes()).toString());

        Integer userId = externalUserDao.createExternalUser(externalUserRegisterRequest);


        InterimExternalUserRegister interimExternalUserRegister = new InterimExternalUserRegister();
        interimExternalUserRegister.setUserId(userId);
        interimExternalUserRegister.setInterimPassword(interimPassword);

        return interimExternalUserRegister;
    }

    @Override
    public ExternalUser getExternalUserById(Integer userId) {
        ExternalUser externalUser = externalUserDao.getExternalUserById(userId);

        if (externalUser != null) {
            return externalUser;
        } else {
            log.warn("查無此 user_id: {}", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ExternalUser getExternalUserByAccount(String account) {
        return externalUserDao.getExternalUserByAccount(account);
    }

    @Override
    public ExternalUser login(ExternalUserLoginRequest externalUserLoginRequest) {
        ExternalUser externalUser = externalUserDao.getExternalUserByAccount(externalUserLoginRequest.getAccount());

        if (externalUser == null) {
            log.warn("帳號: {} 尚未註冊", externalUserLoginRequest.getAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!externalUser.getAccountStatus().getStatus()) {
            log.warn("帳號: {} 已鎖定", externalUser.getAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (externalUser.getPasswordLastModifiedDate() == null) {
            log.warn("帳號: " + externalUser.getAccount() +
                    " 為首次登入，請先變更密碼。");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = DigestUtils.md5DigestAsHex(externalUserLoginRequest.getPassword().getBytes());

        if (externalUser.getPassword().equals(hashedPassword)) {
            if (externalUser.getErrorCount() > 0) {
                externalUserDao.updateErrorCount(externalUser.getAccount(), AuthorizationStatus.SUCCESS);
            }

            return externalUser;
        } else {
            if (externalUser.getErrorCount() < 5) {
                externalUserDao.updateErrorCount(externalUser.getAccount(), AuthorizationStatus.FAILURE);
            }

            log.warn("帳號: {} 密碼輸入錯誤。", externalUser.getAccount());

            if (externalUser.getErrorCount() == 4) {
                externalUserDao.updateAccountStatus(externalUser.getAccount(), AccountStatus.BLOCKED);

                log.warn("密碼輸入錯誤達連續 5 次，帳號已鎖定。");
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void changePassword(ExternalUserChangePasswordRequest changePasswordRequest) {
        ExternalUser externalUser = externalUserDao.getExternalUserByAccount(changePasswordRequest.getAccount());

        if (externalUser == null) {
            log.warn("帳號: {} 尚未註冊", changePasswordRequest.getAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");

        String oldPassword = externalUser.getPassword();
        String newPassword = changePasswordRequest.getNewPassword();

        String newHashedPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        Matcher newPasswordMatcher = pattern.matcher(newPassword);

        boolean isContainsUid = externalUser.getUserUid().contains(newPassword);
        boolean isContainsSbNumber;

        if (oldPassword.equals(newHashedPassword) && newPasswordMatcher.matches()) {
            if (!isContainsUid && !isContainsSbNumber) {
                externalUserDao.updatePassword(externalUser.getAccount(), newHashedPassword);
            }
            System.out.println("ERROR! !");
        } else {
            System.out.println("ERROR! ! !");
        }
    }

}
