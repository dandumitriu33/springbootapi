package com.springbootapi.restapi.config;

import com.springbootapi.restapi.controller.HelloWorldController;
import com.springbootapi.restapi.model.Car;
import com.springbootapi.restapi.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Configuration
public class DataLoader {

    /**
     * This bean will run automatically right after the Spring Boot app starts.
     * By this point, the H2 in-memory database is already created and all tables exist.
     */

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    // This is called by Spring Boot automatically ater app context is ready and H2 is up and the cars table is created
    @Bean
    CommandLineRunner loadSampleCarData(CarRepository carRepository) {
        return args -> {
            // Only load data if the cars table is empty (avoid duplicates on restart)
            if (carRepository.count() == 0) {

                logger.info("Loading sample car data into H2 database...");

                // Try to open the CSV file from src/main/resources/data/cars.csv
                try (InputStream inputStream = getClass().getResourceAsStream("/data/cars.csv");
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                    // Batch insert the cars
                    List<Car> cars = reader.lines()
                            .skip(1)
                            .map(line -> {
                                String[] parts = line.split(",");
                                return new Car(parts[1], parts[2], Integer.parseInt(parts[3]), parts[0]);
                            })
                            .toList();
                    carRepository.saveAll(cars);
                    logger.info("Sample cars successfully loaded!");
                } catch (Exception e) {
                    logger.error("Error loading sample cars: {}", e.getMessage());
                    e.printStackTrace();
                }
            } else {
                logger.info("Cars already exist in the database. Skipping sample load.");
            }
        };
    }
}

