package com.wch.rumormanger.service;

import com.wch.rumormanger.domain.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by weichunhe on 2018/3/7.
 */
@Component
public class ActionService {

    @Autowired
    private InitService initService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private volatile Boolean running = false;

    private List<Table> tables;

    private List<Thread> tasks = new ArrayList<>();


    @PostConstruct
    private void init() {
        tables = initService.getTables();
    }

    class RumorTask implements Runnable {
        private Random random = new Random();

        private int getRandom() {
            if (tables.size() <= 1) {
                return 0;
            }
            return random.nextInt(tables.size() - 1);
        }

        @Override
        public void run() {
            while (running) {
                if (tables.size() > 0) {
                    tables.get(getRandom()).insert(jdbcTemplate);
                }
            }
        }
    }

    public void newRumorMonger() {
        running = true;
        Thread task = new Thread(new RumorTask());
        task.start();
        tasks.add(task);
    }

    public void stop() {
        running = false;
        for (int i = tasks.size() - 1; i >= 0; i--) {
            try {
                tasks.remove(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String status() {
        return "{" +
                "running=" + running +
                ", table=[" + tables.stream().map(table -> table.countStatus()).collect(Collectors.joining(",")) + "]" +
                ", running=" + tasks.size() +
                "}";
    }
}
