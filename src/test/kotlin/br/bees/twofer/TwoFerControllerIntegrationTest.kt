package br.bees.twofer

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

// o que eu quero testar agora
// 1. somente meu controller, sem a interação com a classe TwoFer.kt? --> UnitTest
// 2. a integração do controller com a classe TwoFer.kt?   ---> IntegrationTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TwoFerControllerIntegrationTest(
    private val restTemplate: TestRestTemplate
) : ShouldSpec({
    should("return message 'One for Bob, one for me' on body and HttpStatus=OK when for name parameter is 'Bob'") {
        val response = restTemplate.getForEntity<String>("/two-fer?name=Bob")

        // resultado esperado seja 200 e "One for Bob, one for me'"
        response.statusCode shouldBe HttpStatus.OK
        response.body shouldBe "One for Bob, one for me"
    }

    should("return message 'One for you, one for me' on body and HttpStatus=OK when for name parameter is empty") {
        val response = restTemplate.getForEntity<String>("/two-fer?name=")

        // resultado esperado seja 200 e "One for Bob, one for me"
        response.statusCode shouldBe HttpStatus.OK
        response.body shouldBe "One for you, one for me"
    }
})
