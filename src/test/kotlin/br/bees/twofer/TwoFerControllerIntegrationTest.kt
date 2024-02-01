package br.bees.twofer

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TwoFerControllerIntegrationTest(
    private val restTemplate: TestRestTemplate
) : IntegrationTest() {
    init {
        context("TwoFer Controller") {
            should("return message 'One for Bob, one for me' on body and HttpStatus=OK when for name parameter is 'Bob'") {
                val response = restTemplate.getForEntity<String>("/two-fer?name=Bob")
                response.statusCode shouldBe HttpStatus.OK
                response.body shouldBe "One for Bob, one for me"
            }

            should("return message 'One for you, one for me' on body and HttpStatus=OK when for name parameter is empty") {
                val response = restTemplate.getForEntity<String>("/two-fer?name=")
                response.statusCode shouldBe HttpStatus.OK
                response.body shouldBe "One for you, one for me"
            }
        }
    }
}
