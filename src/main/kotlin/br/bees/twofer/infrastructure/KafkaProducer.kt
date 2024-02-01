package br.bees.twofer.infrastructure

import br.bees.twofer.application.KafkaConsumer
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    fun send(name: String) {
        kafkaTemplate.send("two-fer", name)
    }
}
