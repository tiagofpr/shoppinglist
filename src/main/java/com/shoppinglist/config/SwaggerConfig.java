package com.shoppinglist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Servidor de Desenvolvimento Local");

        Contact contact = new Contact();
        contact.setEmail("seu-email@dominio.com");
        contact.setName("Shopping List API");
        contact.setUrl("https://github.com/seu-usuario");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Shopping List API")
                .version("1.0.0")
                .contact(contact)
                .description("Esta API expõe endpoints para gerenciar listas de compras.")
                .termsOfService("https://meusite.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}