package com.springbootapi.restapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(HelloWorldController.class)
@TestPropertySource(properties = {"env=test"})
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sayHello_shouldReturnHelloWorldMessage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello world"));
    }

    @Test
    void getEnvironment_shouldReturnEnvironmentValue() throws Exception {
        mockMvc.perform(get("/environment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("test"));
    }
}

