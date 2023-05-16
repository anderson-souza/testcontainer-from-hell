package com.aps.integrationtestcontainer.thirdtry;

import com.aps.integrationtestcontainer.Car;
import com.aps.integrationtestcontainer.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarControllerJava extends AbstractIntegrationTest {

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository.save(new Car("Jeta " + Math.random(), "Sedan"));
    }

    @Test
    public void testScenario1() {
        List<Car> cars = carRepository.findAll();
        assertTrue(cars.size() >= 1);
    }
}
