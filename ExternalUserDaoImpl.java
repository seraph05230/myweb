package com.tingweichen.applicationsystem.dao;

import com.tingweichen.applicationsystem.constant.AccountStatus;
import com.tingweichen.applicationsystem.constant.AuthorizationStatus;
import com.tingweichen.applicationsystem.dto.ExternalUserRegisterRequest;
import com.tingweichen.applicationsystem.mapper.ExternalUserRowMapper;
import com.tingweichen.applicationsystem.model.ExternalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExternalUserDaoImpl implements ExternalUserDao {

    private final static Logger log = LoggerFactory.getLogger(ExternalUserDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Override
    public ExternalUser getExternalUserByAccount(String account) {
        String sql = "SELECT user_id, account, password, user_uid, sb_number, status, " +
                "created_date, last_login_date, password_last_modified_date, error_count " +
                "FROM tingwei_exter_user WHERE account = :account";

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);

        List<ExternalUser> externalUserList = parameterJdbcTemplate.query(sql, map, new ExternalUserRowMapper());

        if (externalUserList.size() > 0) {
            return externalUserList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public ExternalUser getExternalUserById(Integer userId) {
        String sql = "SELECT user_id, account, password, user_uid, sb_number, status, " +
                "created_date, last_login_date, password_last_modified_date, error_count " +
                "FROM tingwei_exter_user WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<ExternalUser> externalUserList = parameterJdbcTemplate.query(sql, map, new ExternalUserRowMapper());

        if (externalUserList.size() > 0) {
            return externalUserList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createExternalUser(ExternalUserRegisterRequest externalUserRegisterRequest) {
        String sql = "INSERT INTO tingwei_exter_user (account, password, user_uid, sb_number) " +
                "VALUES (:account, :password, :user_uid, :sb_number)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("account", externalUserRegisterRequest.getAccount())
                .addValue("password", externalUserRegisterRequest.getPassword())
                .addValue("user_uid", externalUserRegisterRequest.getUserUid())
                .addValue("sb_number", externalUserRegisterRequest.getSb_number());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        parameterJdbcTemplate.update(sql, parameterSource, keyHolder, new String[]{"user_id"});

        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateErrorCount(String account, AuthorizationStatus authorizationStatus) {
        String plusSql = "UPDATE tingwei_exter_user " +
                "SET error_count = (SELECT error_count FROM tingwei_exter_user WHERE account = :account) + 1 " +
                "WHERE account = :account";

        String resetSql = "UPDATE tingwei_exter_user " +
                "SET error_count = 0 " +
                "WHERE account = :account";

        Map<String, Object> map = new HashMap<>();

        map.put("account", account);

        if (authorizationStatus.getStatus()) {
            parameterJdbcTemplate.update(resetSql, map);
        } else {
            parameterJdbcTemplate.update(plusSql, map);
        }
    }

    @Override
    public void updateAccountStatus(String account, AccountStatus accountStatus) {
        String sql = "UPDATE tingwei_exter_user SET status = :accountStatus WHERE account = :account";

        Map<String, Object> map = new HashMap<>();

        map.put("accountStatus", accountStatus.name());
        map.put("account", account);

        parameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updatePassword(String account, String newHashedPassword) {
        String sql = "UPDATE tingwei_exter_user SET password = :newPassword" +
                " WHERE account = :account";

        Map<String, Object> map = new HashMap<>();

        map.put("newPassword", newHashedPassword);
        map.put("account", account);

        parameterJdbcTemplate.update(sql, map);
    }
}
