package com.aps.integrationtestcontainer.firsttry

import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.images.builder.Transferable
import org.testcontainers.utility.MountableFile

const val DATABASE_NAME = "testdb"
const val MYSQL_VERSION = "8.0.33"

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [DatabaseContainerConfiguration.Initializer::class])
abstract class DatabaseContainerConfiguration {


    companion object {

        val LOGGER = LoggerFactory.getLogger(javaClass)

        private const val memoryInBytes = 384L * 1024L * 1024L

        private const val memorySwapInBytes = 384L * 1024L * 1024L
        private val logConsumer = Slf4jLogConsumer(LOGGER)

        val mysqlContainer = MySQLContainer<Nothing>("mysql:latest").apply {
            withDatabaseName(DATABASE_NAME)
            withUsername("root")
            withPassword("password")
            withConnectTimeoutSeconds(20)
            withCopyFileToContainer(
                MountableFile.forClasspathResource(
                    "optmized-memory-integration-tests.cnf",
                    Transferable.DEFAULT_DIR_MODE
                ),
                "/etc/mysql/conf.d",

                )
            withCreateContainerCmdModifier {
                it.hostConfig?.withMemory(memoryInBytes)
                    ?.withMemorySwap(memorySwapInBytes)
                    ?.withCpuPercent(50L)
            }
            withLogConsumer(logConsumer)
        }

    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            mysqlContainer.start()

            val mysqlPort = mysqlContainer.getMappedPort(3306)

            val jdbcUrl = "jdbc:mysql://localhost:$mysqlPort/$DATABASE_NAME"

            TestPropertyValues.of(
                "spring.datasource.password=password",
                "spring.datasource.username=root",
                "spring.datasource.url=$jdbcUrl"
            ).applyTo(configurableApplicationContext.environment)
        }
    }

}