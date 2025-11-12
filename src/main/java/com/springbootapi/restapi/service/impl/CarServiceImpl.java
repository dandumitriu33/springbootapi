package com.springbootapi.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootapi.restapi.model.Car;
import com.springbootapi.restapi.repository.CarRepository;
import com.springbootapi.restapi.service.CarService;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository repository;

    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Car create(Car car) {
        return repository.save(car);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Car> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Car> findByVin(String vin) {
        return Optional.ofNullable(repository.findByVin(vin));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

