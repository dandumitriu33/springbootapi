package com.springbootapi.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapi.restapi.model.Car;
import com.springbootapi.restapi.security.JwtService;
import com.springbootapi.restapi.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @MockitoBean
    private JwtService jwtService;  // Mock bean to satisfy JwtAuthFilter dependency

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testInvalidCarReturnsBadRequest() throws Exception {
        String invalidJson = """
            {
              "make": "",
              "model": "Civic",
              "manufactureYear": 1000,
              "vin": "ab"
            }
            """;

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.make").value("Make is required"))
                .andExpect(jsonPath("$.vin").value("VIN must be between 5 and 20 characters"))
                .andExpect(jsonPath("$.manufactureYear").value("Year must be after the invention of automobiles (1886)"));
    }

    @Test
    void testCreateCar() throws Exception {
        Car requestCar = new Car();
        requestCar.setMake("Honda");
        requestCar.setModel("Civic");
        requestCar.setManufactureYear(2025);
        requestCar.setVin("1HGCV1F34JA123555");

        // Mocked return from create
        Car persistedCar = new Car();
        persistedCar.setId(1L);
        persistedCar.setMake(requestCar.getMake());
        persistedCar.setModel(requestCar.getModel());
        persistedCar.setManufactureYear(requestCar.getManufactureYear());
        persistedCar.setVin(requestCar.getVin());

        // Mock stub
        Mockito.when(carService.create(Mockito.any(Car.class)))
                .thenReturn(persistedCar);

        ResultActions result = mockMvc.perform(
                post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestCar))
        );

        result.andExpect(status().isCreated())                // 201
                .andExpect(header().string("Location", "/api/cars/1"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.make").value("Honda"))
                .andExpect(jsonPath("$.model").value("Civic"))
                .andExpect(jsonPath("$.manufactureYear").value(2025))
                .andExpect(jsonPath("$.vin").value("1HGCV1F34JA123555"));
    }

}
