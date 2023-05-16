package com.aps.integrationtestcontainer.compose

import com.aps.integrationtestcontainer.firsttry.MYSQL_URL
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.time.Duration

const val DATABASE_NAME = "testdb"
const val MYSQL_VERSION = "8.0.33"
const val SERVICE_NAME = "mysql-db"
const val SERVICE_PORT = 3306

@SpringBootTest
abstract class DockerComposeBaseClass {

    companion object {
        val dockerComposeContainer: DockerComposeContainer<*> =
            DockerComposeContainer(File("src/test/resources/docker-compose.yml"))
                .withExposedService(SERVICE_NAME, SERVICE_PORT)
                .waitingFor(SERVICE_NAME, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))

        init {
            dockerComposeContainer.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            val serviceHost = dockerComposeContainer.getServiceHost(SERVICE_NAME, SERVICE_PORT)
            val servicePort = dockerComposeContainer.getServicePort(SERVICE_NAME, SERVICE_PORT)

            println("serviceHost: $serviceHost")
            println("servicePort: $servicePort")

            val MYSQL_URL = "jdbc:tc:mysql://$serviceHost:$servicePort/$DATABASE_NAME"
            registry.add("spring.datasource.url") { MYSQL_URL }
            registry.add("spring.datasource.password") { "test" }
            registry.add("spring.datasource.username") { "root" }
        }
    }

}