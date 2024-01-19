package com.tingweichen.applicationsystem.mapper;

import com.tingweichen.applicationsystem.constant.AccountStatus;
import com.tingweichen.applicationsystem.model.ExternalUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExternalUserRowMapper implements RowMapper<ExternalUser> {

    @Override
    public ExternalUser mapRow(ResultSet resultSet, int i) throws SQLException {
        ExternalUser externalUser = new ExternalUser();

        externalUser.setUserId(resultSet.getInt("user_id"));
        externalUser.setAccount(resultSet.getString("account"));
        externalUser.setPassword(resultSet.getString("password"));
        externalUser.setUserUid(resultSet.getString("user_uid"));
        externalUser.setSbNumber(resultSet.getString("sb_number"));
        externalUser.setAccountStatus(AccountStatus.valueOf(resultSet.getString("status")));
        externalUser.setCreatedDate(resultSet.getTimestamp("created_date"));
        externalUser.setLastLoginDate(resultSet.getTimestamp("last_login_date"));
        externalUser.setPasswordLastModifiedDate(resultSet.getTimestamp("password_last_modified_date"));
        externalUser.setErrorCount(resultSet.getInt("error_count"));

        return externalUser;
    }
}
