package com.example.middleware_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/api")
public class MiddlewareController {

    private final RestClient restClient;

    public MiddlewareController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<String> getCustomerDetails(@PathVariable int id) {

        try {
            String response = restClient.get()
                    .uri("http://localhost:8080/internal/products/" + id)
                    .retrieve()
                    .body(String.class);

            return ResponseEntity.ok(response);

        } catch (RestClientException e) {

        }
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body("Downstream api is down");
    }
}
