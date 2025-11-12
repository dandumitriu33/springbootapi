package com.springbootapi.restapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springbootapi.restapi.model.Car;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private CarRepository repository;

    @Test
    void testSaveAndFindByVin() {
        Car car = new Car("Toyota", "Camry", 2020, "VIN12345");
        repository.save(car);

        Car found = repository.findByVin("VIN12345");
        assertThat(found).isNotNull();
        assertThat(found.getMake()).isEqualTo("Toyota");
    }
}

