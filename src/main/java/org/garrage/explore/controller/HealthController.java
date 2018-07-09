package org.garrage.explore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class HealthController {

    @RequestMapping(value = "/health")
    public Map<String,String> healthCheck() {
        Map m = new HashMap<String,String>();
        m.put("success","true");
        m.put("message","Chill dude, I'm up!");
        return m;
    }
}
