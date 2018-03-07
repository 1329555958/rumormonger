package com.wch.rumormanger.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
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
                columnNames.append(column.getColumnName()).append(",");
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
        LOG.debug("sql:{},parameter:{}", insertSql, parameters);
        jdbcTemplate.update(insertSql, parameters);
        insertCount.incrementAndGet();
        LOG.debug("insert into {},count {},", name, insertCount.get());
    }
//sql:insert into gop2.t_batch_payaccount_detail(BATCH_NO,OUTER_TRADE_NO,TRADE_VOUCHER_NO,UNFROZEN_VOUCHER_NO,SOURCE_TRADE_VOUCHER_NO,PAYEE_IDENTITY_NO,PAYEE_IDENTITY_TYPE,PAYEE_ACCOUNT_TYPE,AMOUNT,TRADE_STATUS,HAS_SUM,BATCH_FLAG,ERROR_CODE,ERROR_MSG,EXTENSION,GMT_CREATE,PAY_SUBMIT_TIME,GMT_MODIFIED,MEMO)values(:BATCH_NO,:OUTER_TRADE_NO,:TRADE_VOUCHER_NO,:UNFROZEN_VOUCHER_NO,:SOURCE_TRADE_VOUCHER_NO,:PAYEE_IDENTITY_NO,:PAYEE_IDENTITY_TYPE,:PAYEE_ACCOUNT_TYPE,:AMOUNT,:TRADE_STATUS,:HAS_SUM,:BATCH_FLAG,:ERROR_CODE,:ERROR_MSG,:EXTENSION,:GMT_CREATE,:PAY_SUBMIT_TIME,:GMT_MODIFIED,:MEMO),parameter:{PAYEE_IDENTITY_TYPE=7B6U7PMGJ2, BATCH_NO=kTkvlX68c6M_9sO99JSjWVaFahpKo77p, EXTENSION=C1p_ynnJEd8kSu7y5mNcLrAmGYoWLaWS5zSHKoVw0rF8DgfoPi, PAYEE_ACCOUNT_TYPE=ufFgDAikSdUKbLSgImAL, TRADE_STATUS=bN, TRADE_VOUCHER_NO=e7Q4EABAeCDzHb2oXRoWLDCg7SYO1vtI, HAS_SUM=R, ERROR_CODE=wVL7CsKvb1, GMT_CREATE=Wed Mar 07 18:22:32 CST 2018, PAYEE_IDENTITY_NO=54JPc2ekWqhpWvQKSsxqRqPrBfxJ4x6G, AMOUNT=, UNFROZEN_VOUCHER_NO=DAmUQDs_gHC0Lor_9GXF1-JfatA8H25W, OUTER_TRADE_NO=n7rmK-s1QmcyGXFTlSV3XHn0_N_TKabyN8gxE6iQ2_2cdGcdNr, GMT_MODIFIED=Wed Mar 07 18:22:32 CST 2018, ERROR_MSG=sI3h3cAWwqtpiM4vXiRqh8n_QgQzJ0bEyyjkmiXJDP7_sYJRuk, BATCH_FLAG=G6GvsDHpHwS62Yn-8DwRVrKCiw79djKxJiPx7lfxMwOdmikq2x, MEMO=5IaXuqyxEq30PMuIgfjW9fMR8I7ffTEl8D49qgsFrSUwQdvbBI, SOURCE_TRADE_VOUCHER_NO=V_4w-R4mUvwA4oova0yobbQe5PnFtOM6, PAY_SUBMIT_TIME=Wed Mar 07 18:22:32 CST 2018}
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
