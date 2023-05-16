package com.aps.integrationtestcontainer;

import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository : JpaRepository<Car, Long> {
}