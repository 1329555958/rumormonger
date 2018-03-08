package com.wch.rumormanger.controller;

import com.wch.rumormanger.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by weichunhe on 2018/3/8.
 */
@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @RequestMapping("/newRumorMonger/{count}")
    public String newRumorMonger(@PathVariable int count) {
        if (count < 1) {
            actionService.newRumorMonger();
        } else {
            while (count-- > 0) {
                actionService.newRumorMonger();
            }
        }
        return actionService.status();
    }

    @RequestMapping("/stop")
    public String stop() {
        actionService.stop();
        return actionService.status();
    }

    @RequestMapping("/status")
    public String status() {
        return actionService.status();
    }
}
