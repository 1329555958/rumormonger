package com.wch.rumormanger.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * table domain object
 */
public class Table {
    private static Logger LOG = LoggerFactory.getLogger(Table.class);

    private String name; //schema.table

    private List<Column> columns;

    private String insertSql;

    private AtomicLong insertCount = new AtomicLong();

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
        initInsertSql();
    }

    private void initInsertSql() {
        StringBuilder sql = new StringBuilder();
        StringBuilder columnNames = new StringBuilder();
        StringBuilder columnParamterNames = new StringBuilder();

        columns.forEach(column -> {
            if (column.isInsertColumn()) {
                columnNames.append("`").append(column.getColumnName()).append("`").append(",");
                columnParamterNames.append(":").append(column.getColumnName()).append(",");
            }
        });
        if (columnNames.length() != 0) {
            columnNames.deleteCharAt(columnNames.lastIndexOf(","));
            columnParamterNames.deleteCharAt(columnParamterNames.lastIndexOf(","));
        }
        sql.append("insert into ")
                .append(getName())
                .append("(")
                .append(columnNames)
                .append(")values(")
                .append(columnParamterNames)
                .append(")");
        insertSql = sql.toString();
    }

    public void insert(NamedParameterJdbcTemplate jdbcTemplate) {
        Map<String, Object> parameters = new HashMap<>();
        columns.forEach(column -> {
            if (column.isInsertColumn()) {
                parameters.put(column.getColumnName(), column.getColumnValueProducer().getValue());
            }
        });
        LOG.debug("sql={},parameter={}", insertSql, parameters);
        try {
            jdbcTemplate.update(insertSql, parameters);
            insertCount.incrementAndGet();
        } catch (Exception e) {
            LOG.warn("insert error, sql:{},error:{}", insertSql, e.getMessage());
        }
    }

    public String countStatus() {
        return name + "{" +
                "insertCount=" + insertCount.get() +
                "}";
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
