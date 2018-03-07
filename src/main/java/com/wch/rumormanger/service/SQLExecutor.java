package com.wch.rumormanger.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by weichunhe on 2018/3/7.
 */
@Service
public class SQLExecutor {
    private NamedParameterJdbcTemplate jdbcTemplate;

    private void exec(String sql){
    }
}
