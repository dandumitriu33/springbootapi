package com.springbootapi.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springbootapi.restapi.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByVin(String vin);
}
