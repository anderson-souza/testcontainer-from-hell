package com.aps.integrationtestcontainer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IntegrationTestcontainerApplication

fun main(args: Array<String>) {
	runApplication<IntegrationTestcontainerApplication>(*args)
}
