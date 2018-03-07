package com.wch.rumormanger.service;

import com.wch.rumormanger.domain.Column;
import com.wch.rumormanger.domain.Table;
import com.wch.rumormanger.entity.Columns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weichunhe on 2018/3/7.
 */
@Service
public class InitService {

    private static Logger LOG = LoggerFactory.getLogger(InitService.class);
    private List<Table> tables;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private synchronized void init() {
        Map<String, List<Column>> tableMap = new HashMap<>();
        List<Columns> columnsList = getInformation();
        for (Columns columnInfo : columnsList) {
            parseColumnInfo(tableMap, columnInfo);
        }

        tables = new ArrayList<>();
        tableMap.forEach((k, v) -> {
            tables.add(new Table(k, v));
        });
        LOG.info("tables:{}", tables);
    }

    public List<Table> getTables() {
        if (tables == null) {
            init();
        }
        return tables;
    }

    String sql = "SELECT  table_schema,table_name,column_name,ordinal_position,IS_NULLABLE,data_type,CHARACTER_MAXIMUM_LENGTH,column_key,extra FROM information_schema.columns t WHERE t.TABLE_SCHEMA NOT IN('information_schema','mysql','performance_schema') ORDER BY table_schema,table_name,ordinal_position";

    private List<Columns> getInformation() {
        Map<String, Object> parameter = new HashMap<>();
        List<Columns> columnsList = jdbcTemplate.query(sql, parameter, new BeanPropertyRowMapper<>(Columns.class));

        return columnsList;
    }

    /**
     * 解析列信息合成表信息
     *
     * @param tableMap
     * @param columnInfo
     */
    private void parseColumnInfo(Map<String, List<Column>> tableMap, Columns columnInfo) {
        String table = columnInfo.getTable_schema() + "." + columnInfo.getTable_name();
        List<Column> columns = tableMap.get(table);
        if (columns == null) {
            columns = new ArrayList<>();
            tableMap.put(table, columns);
        }
        Column column = Column.fromColumns(columnInfo);
        columns.add(column);
    }
}
