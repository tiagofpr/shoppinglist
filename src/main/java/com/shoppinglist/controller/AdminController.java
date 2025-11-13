package com.shoppinglist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administração", description = "Endpoints administrativos")
public class AdminController {

    @GetMapping("/endpoints")
    @Operation(summary = "Listar endpoints disponíveis")
    public Map<String, Object> listEndpoints() {
        Map<String, Object> response = new HashMap<>();

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("GET /api/health", "Health check da API");
        endpoints.put("GET /api/test", "Endpoint de teste");
        endpoints.put("GET /api/info", "Informações da API");
        endpoints.put("GET /api/admin/endpoints", "Lista de endpoints");
        endpoints.put("GET /api/usuarios", "Listar usuários");
        endpoints.put("POST /api/usuarios", "Criar usuário");
        endpoints.put("GET /api/categorias", "Listar categorias");
        endpoints.put("POST /api/categorias", "Criar categoria");

        response.put("endpoints", endpoints);
        response.put("swagger", "http://localhost:8080/swagger-ui.html");
        response.put("api-docs", "http://localhost:8080/v3/api-docs");
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return response;
    }
}