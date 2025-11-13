package com.shoppinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.shoppinglist")
@EntityScan("com.shoppinglist.model")
@EnableJpaRepositories("com.shoppinglist.repository")
public class ShoppingListApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingListApplication.class, args);
    }
}