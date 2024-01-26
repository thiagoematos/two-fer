package br.bees.twofer

import br.bees.twofer.domain.TwoFerInterface
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val twoFer: TwoFerInterface
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    fun send(name: String) {
        val message = twoFer.message(name)
        LOGGER.info("Sending message: $message")
        kafkaTemplate.send("two-fer", message)
    }
}
