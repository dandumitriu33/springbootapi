package com.springbootapi.restapi.controller;

import com.springbootapi.restapi.security.JwtService;
import com.springbootapi.restapi.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;


@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @MockitoBean
    private JwtService jwtService;  // Mock bean to satisfy JwtAuthFilter dependency

    @Test
    void testInvalidCarReturnsBadRequest() throws Exception {
        String invalidJson = """
            {
              "make": "",
              "model": "Civic",
              "manufactureYear": 1000,
              "vin": ""
            }
            """;

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.make").value("Make is required"))
                .andExpect(jsonPath("$.vin").value("VIN is required"))
                .andExpect(jsonPath("$.manufactureYear").value("Year must be after the invention of automobiles (1886)"));
    }
}
