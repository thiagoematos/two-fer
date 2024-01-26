package br.bees.twofer

import io.kotest.assertions.nondeterministic.eventually
import io.kotest.assertions.nondeterministic.eventuallyConfig
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import kotlin.time.Duration.Companion.seconds

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class TwoFerEmbeddedKafkaIntegrationTest(
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

    should("send and receive a empty message") {
        producer.send(name = "")

        eventually(
            eventuallyConfig {
                duration = 10.seconds
                interval = 2.seconds
            }
        ) {
            consumer.payload shouldBe "One for you, one for me"
        }
    }
})
