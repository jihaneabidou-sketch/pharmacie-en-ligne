package com.Pharmacie.enLigne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 1. Indique à Spring où trouver tes entités JPA (@Entity)
@EntityScan(basePackages = "com.Pharmacie.enLigne.models")

// 2. Indique à Spring où trouver tes interfaces de base de données (@Repository)
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = "com.Pharmacie.repositories")

// 3. Indique à Spring où scanner absolument TOUTES tes annotations (@Component, @Service, @RestController)
@ComponentScan(basePackages = {
    "com.Pharmacie.enLigne",
    "com.Pharmacie.services",
    "com.Pharmacie.repositories"
})
public class EnLigneApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnLigneApplication.class, args);
    }
}