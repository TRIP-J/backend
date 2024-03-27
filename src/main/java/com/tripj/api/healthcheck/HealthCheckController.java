package com.tripj.api.healthcheck;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Slf4j
@RestController
@RequestMapping("/api/admin")
public class HealthCheckController {

    @GetMapping("/test")
    public String healthCheck() {
        log.info("admin test success!");
        return "admin test success";
    }

}
