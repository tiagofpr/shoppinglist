package com.shoppinglist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Sistema", description = "Endpoints para verificação do sistema")
public class TestController {

    @GetMapping("/health")
    @Operation(summary = "Verificar saúde da API", description = "Retorna o status atual da API")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "API está funcionando!");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        response.put("version", "1.0.0");
        return response;
    }

    @GetMapping("/test")
    @Operation(summary = "Endpoint de teste", description = "Endpoint simples para testar a API")
    public Map<String, String> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Test endpoint funcionando!");
        response.put("status", "success");
        return response;
    }

    @GetMapping("/info")
    @Operation(summary = "Informações da API", description = "Retorna informações sobre a API")
    public Map<String, String> info() {
        Map<String, String> response = new HashMap<>();
        response.put("name", "Shopping List API");
        response.put("version", "1.0.0");
        response.put("description", "API para gerenciamento de listas de compras");
        response.put("documentation", "http://localhost:8080/swagger-ui.html");
        return response;
    }
}