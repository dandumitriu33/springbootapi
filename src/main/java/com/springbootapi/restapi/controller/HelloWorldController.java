package com.springbootapi.restapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;


@RestController
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Value("${env:default}")
    private String environment;

    @GetMapping("/hello")
    public Map<String, String> sayHello() {
        logger.info("Method sayHello reached.");
        // Return a JSON object with "message": "Hello world"
        return Collections.singletonMap("message", "Hello world");
    }

    @GetMapping("/environment")
    public Map<String, String> getEnvironment() {
        logger.info("Method getEnvironment reached.");
        return Collections.singletonMap("message", environment);
    }
}
