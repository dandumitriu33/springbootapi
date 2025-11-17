package com.springbootapi.restapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @Min(value = 1886, message = "Year must be after the invention of automobiles (1886)")
    @Max(value = 2050, message = "Year cannot be in the far future")
    private int manufactureYear;

    @NotBlank(message = "VIN is required")
    @Size(min = 5, max = 20, message = "VIN must be between 5 and 20 characters")
    private String vin;

    public Car() {}

    public Car(String make, String model, int manufactureYear, String vin) {
        this.make = make;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.vin = vin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public int getManufactureYear() { return manufactureYear; }
    public void setManufactureYear(int manufactureYear) { this.manufactureYear = manufactureYear; }
}

