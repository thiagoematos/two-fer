package br.bees.twofer

import io.kotest.assertions.nondeterministic.eventually
import io.kotest.assertions.nondeterministic.eventuallyConfig
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import kotlin.time.Duration.Companion.seconds

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["spring.kafka.consumer.auto-offset-reset=earliest"])
@Testcontainers
class TwoFerTestContainersIntegrationTest(
    @Autowired private val consumer: KafkaConsumer,
    @Autowired private val producer: KafkaProducer
) : ShouldSpec({
    should("send and receive a message") {
        producer.send(name = "Bob")

        eventually(
            eventuallyConfig {
                duration = 10.seconds
                interval = 2.seconds
            }
        ) {
            consumer.payload shouldBe "One for Bob, one for me"
        }
    }
}) {
    private companion object {
        @Container
        private val kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")).apply {
            withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers)
        }
    }
}
