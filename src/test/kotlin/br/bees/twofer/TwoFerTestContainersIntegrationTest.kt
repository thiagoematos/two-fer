package br.bees.twofer

import br.bees.twofer.infrastructure.KafkaProducer
import br.bees.twofer.infrastructure.TwoFerRepository
import io.kotest.assertions.nondeterministic.eventually
import io.kotest.assertions.nondeterministic.eventuallyConfig
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.time.Duration.Companion.seconds

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["spring.kafka.consumer.auto-offset-reset=earliest"])
@Testcontainers
class TwoFerTestContainersIntegrationTest(
    @Autowired private val producer: KafkaProducer,
    @Autowired private val twoFerRepository: TwoFerRepository
) : IntegrationTest() {
    init {
        context("Kafka Test") {
            should("send and receive a message") {
                producer.send(name = "Bob")

                eventually(
                    eventuallyConfig {
                        duration = 10.seconds
                        interval = 2.seconds
                    }
                ) {
                    twoFerRepository.getMessageByName("Bob")?.shouldBe("One for Bob, one for me")
                }
            }
        }
    }
}
