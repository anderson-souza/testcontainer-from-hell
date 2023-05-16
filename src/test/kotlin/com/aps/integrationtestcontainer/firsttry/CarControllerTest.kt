package com.aps.integrationtestcontainer.firsttry

import com.aps.integrationtestcontainer.Car
import com.aps.integrationtestcontainer.CarRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.math.roundToInt

class CarControllerTest : DatabaseContainerConfiguration() {

    @Autowired
    private lateinit var carRepository: CarRepository

    @BeforeEach
    fun setUp() {
        val random = Math.random().roundToInt()
        carRepository.save(Car("Jeta ${random}", "Sedan"))
    }

    @Test
    fun `test 1`() {
        val cars = carRepository.count()
        println(carRepository.findAll())
        assert(cars >= 1)
    }

    @Test
    fun `Test 2`() {
        val cars = carRepository.count()
        println(carRepository.findAll())
        assert(cars >= 1)
    }

    @Test
    fun `Test 3`() {
        val cars = carRepository.count()
        println(carRepository.findAll())
        assert(cars >= 1)
    }

    @Test
    fun `Test 4`() {
        val cars = carRepository.count()
        println(carRepository.findAll())
        assert(cars >= 1)
    }

    @Test
    fun `Test 5`() {
        val cars = carRepository.count()
        println(carRepository.count())
        assert(cars >= 1)
    }
}