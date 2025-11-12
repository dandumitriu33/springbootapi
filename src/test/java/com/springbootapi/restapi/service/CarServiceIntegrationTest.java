package com.springbootapi.restapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springbootapi.restapi.model.Car;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CarServiceIntegrationTest {

    @Autowired
    private CarService carService;

    @Test
    void createAndRetrieveCar() {
        Car saved = carService.create(new Car("Honda", "Civic", 2021, "VIN67890"));
        assertThat(saved.getId()).isNotNull();

        Car found = carService.findByVin("VIN67890").orElseThrow();
        assertThat(found.getModel()).isEqualTo("Civic");
    }
}

