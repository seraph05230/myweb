package com.tingweichen.applicationsystem.service;

import com.tingweichen.applicationsystem.dao.ExternalUserPasswordDao;
import com.tingweichen.applicationsystem.model.ExternalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExternalUserPasswordServiceImpl implements ExternalUserPasswordService {

    private final static Logger log = LoggerFactory.getLogger(ExternalUserPasswordServiceImpl.class);

    @Autowired
    private ExternalUserPasswordDao externalUserPasswordDao;

    @Override
    public Integer createExternalUserPasswordInfo(ExternalUser externalUser) {

        Integer userPasswordId = externalUserPasswordDao.createExternalUserPasswordInfo(externalUser);

        if (userPasswordId != null) {
            return userPasswordId;
        } else {
            log.warn("新增 user_id: {} 的密碼紀錄表有異常！", externalUser.getUserId());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
