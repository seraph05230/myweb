package com.tingweichen.applicationsystem.dao;

import com.tingweichen.applicationsystem.model.ExternalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalUserPasswordDaoImpl implements ExternalUserPasswordDao {

    private final static Logger log = LoggerFactory.getLogger(ExternalUserPasswordDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Override
    public Integer createExternalUserPasswordInfo(ExternalUser externalUser) {
        String sql = "INSERT INTO tingwei_exter_user_password (user_id, previous_password) " +
                "VALUES (:userId, :previous_password)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("userId", externalUser.getUserId())
                .addValue("previous_password", externalUser.getPassword());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        parameterJdbcTemplate.update(sql, parameterSource, keyHolder, new String[]{"user_password_id"});

        return keyHolder.getKey().intValue();
    }
}
