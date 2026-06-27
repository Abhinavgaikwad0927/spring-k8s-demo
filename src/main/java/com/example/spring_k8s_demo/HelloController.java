package com.example.spring_k8s_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello from Kubernetes!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
