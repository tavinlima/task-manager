package com.personal.task_manager.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController

public class RestController {
    @GetMapping("/")
    public String home() {
        return "Servidor Spring Boot está rodando!";
    }
}