package com.springbootapi.restapi.service;

import java.util.List;
import java.util.Optional;
import com.springbootapi.restapi.model.Car;

public interface CarService {
    Car create(Car car);
    List<Car> findAll();
    Optional<Car> findById(Long id);
    Optional<Car> findByVin(String vin);
    void delete(Long id);
}

