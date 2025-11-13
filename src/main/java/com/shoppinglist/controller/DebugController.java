package com.shoppinglist.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/usuarios")
    public Map<String, Object> debugUsuarios() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Endpoint de usuários está funcionando!");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        response.put("status", "SUCCESS");
        return response;
    }

    @GetMapping("/test-all")
    public Map<String, Object> testAllEndpoints() {
        Map<String, Object> response = new HashMap<>();
        response.put("/api/health", "Health Check");
        response.put("/api/test", "Test Endpoint");
        response.put("/api/debug/usuarios", "Debug Usuários");
        response.put("/api/usuarios", "Usuários Principal");
        response.put("status", "Todos os endpoints listados");
        return response;
    }
}