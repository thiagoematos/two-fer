package br.bees.twofer.application

import br.bees.twofer.infrastructure.TwoFer
import br.bees.twofer.infrastructure.TwoFerRepository
import br.bees.twofer.domain.TwoFerInterface
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.UUID.randomUUID

@Component
class KafkaConsumer(
    private val twoFerRepository: TwoFerRepository,
    private val twofer: TwoFerInterface
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    private var _payload: String = ""

    @KafkaListener(topics = ["two-fer"])
    fun receiver(consumerRecord: ConsumerRecord<String, String>) {
        LOGGER.info("Received message: ${consumerRecord.value()}")
        val name = consumerRecord.value()
        val message = twofer.message(name)
        _payload = message

        twoFerRepository.saveAndFlush(
            TwoFer(
                id = randomUUID(),
                name = name,
                message = message
            )
        )
    }

    val payload: String get() = this._payload
}
