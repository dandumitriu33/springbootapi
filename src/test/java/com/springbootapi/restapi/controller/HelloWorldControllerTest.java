package com.springbootapi.restapi.controller;

import com.springbootapi.restapi.security.JwtAuthFilter;
import com.springbootapi.restapi.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloWorldController.class)
@TestPropertySource(properties = {"env=test"})
@AutoConfigureMockMvc(addFilters = false) // Replaced the adding of the user on the public /hello GET
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;  // Mock bean to satisfy JwtAuthFilter dependency

    @Test
//    @WithMockUser(username = "user", roles = {"USER"}) // Even though /hello is public, filtering is still loaded and can't remove for tests
    void sayHello_shouldReturnHelloWorldMessage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello world"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // Even though /hello is public, filtering is still loaded and can't remove for tests
    void getEnvironment_shouldReturnEnvironmentValue() throws Exception {
        mockMvc.perform(get("/environment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("test"));
    }
}

