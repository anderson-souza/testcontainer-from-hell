package com.aps.integrationtestcontainer.firsttry

import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.GenericContainer

const val DATABASE_NAME = "testdb"
const val MYSQL_VERSION = "8.0.33"
const val MYSQL_URL = "jdbc:mysql:$MYSQL_VERSION:///$DATABASE_NAME"

@SpringBootTest
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [DatabaseContainerConfiguration.Initializer::class])
abstract class DatabaseContainerConfiguration {

    companion object {
//        private val mysqlContainer = MySQLContainer<Nothing>("mysql:latest").apply {
//            withDatabaseName(DATABASE_NAME)
//            withUsername("root")
//            withPassword("root")
//            withConnectTimeoutSeconds(200)
//        }

        private val mysqlContainer = GenericContainer<Nothing>("mysql:latest").apply {
            withExposedPorts(3306)
            withEnv("MYSQL_ROOT_PASSWORD", "password")
            withEnv("MYSQL_DATABASE", DATABASE_NAME)
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            mysqlContainer.start()

            val mysqlPort = mysqlContainer.getMappedPort(3306)

            val jdbcUrl = "jdbc:mysql://localhost:$mysqlPort/$DATABASE_NAME?useSSL=false&allowPublicKeyRetrieval=true"

            TestPropertyValues.of(
                "spring.datasource.password=password",
                "spring.datasource.username=root",
                "spring.datasource.url=$jdbcUrl"
            ).applyTo(configurableApplicationContext.environment)
        }
    }

}