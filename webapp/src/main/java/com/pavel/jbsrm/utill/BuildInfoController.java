package com.pavel.jbsrm.utill;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/info")
public class BuildInfoController {

    @Value("${info.app.name}")
    private String name;
    @Value("${info.app.description}")
    private String description;
    @Value("${info.app.version}")
    private String version;

    @GetMapping
    public Map buildInfo() {
        Map<String, String> buildInfo = new HashMap<>();
        buildInfo.put("App name", name);
        buildInfo.put("App description", description);
        buildInfo.put("App version", version);

        return buildInfo;
    }
}
