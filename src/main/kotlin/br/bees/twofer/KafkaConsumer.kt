package br.bees.twofer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    private var _payload: String = ""

    @KafkaListener(topics = ["two-fer"])
    fun receiver(consumerRecord: ConsumerRecord<String, String>) {
        LOGGER.info("Received message: ${consumerRecord.value()}")
        _payload = consumerRecord.value()
    }

    // write a method to return the payload
    val payload: String get() = this._payload
}
