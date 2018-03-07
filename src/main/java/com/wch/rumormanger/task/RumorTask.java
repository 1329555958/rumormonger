package com.wch.rumormanger.task;

import com.wch.rumormanger.domain.Table;
import com.wch.rumormanger.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

/**
 * Created by weichunhe on 2018/3/7.
 */
@Component
public class RumorTask {

    @Autowired
    private InitService initService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void task() {
        List<Table> tables = initService.getTables();
        Random random = new Random();
        tables.get(random.nextInt(tables.size() - 1)).insert(jdbcTemplate);
    }
}
