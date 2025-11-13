package com.shoppinglist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

//    @GetMapping("/health")
//    public Map<String, String> healthCheck() {
//        Map<String, String> response = new HashMap<>();
//        response.put("status", "UP");
//        response.put("timestamp", java.time.LocalDateTime.now().toString());
//        response.put("service", "Shopping List API");
//        return response;
//    }

//    @GetMapping("/test")
//    public Map<String, String> test() {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "API está funcionando!");
//        response.put("endpoint", "/api/test");
//        return response;
//    }
}