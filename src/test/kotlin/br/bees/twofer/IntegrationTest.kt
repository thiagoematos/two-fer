package br.bees.twofer

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.spec.style.scopes.ShouldSpecContainerScope
import org.flywaydb.core.Flyway
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
abstract class IntegrationTest : ShouldSpec() {

    final override fun context(name: String, test: suspend ShouldSpecContainerScope.() -> Unit) {
        super.context(name, test)
    }

    companion object {
        @Container
        private val kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
            .apply {
                start()
            }

        @Container
        private val postgresqlContainer = PostgreSQLContainer("postgres:15-alpine")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("credit-dunning-service-db")
            .withExposedPorts(5432)
            .apply {
                start()

                Flyway
                    .configure()
                    .dataSource(jdbcUrl, username, password)
                    .locations("classpath:db/migration")
                    .load()
                    .migrate()
            }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers)
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
        }
    }
}
