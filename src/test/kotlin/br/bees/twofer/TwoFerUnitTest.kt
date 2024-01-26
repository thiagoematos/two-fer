package br.bees.twofer

import br.bees.twofer.domain.TwoFer
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class TwoFerUnitTest : ShouldSpec({
    should("return message 'One for Bob, one for me' when for name is 'Bob'") {
        // given
        val nome = "Bob"

        // when
        val message = TwoFer().message(nome)

        // then
        message shouldBe "One for Bob, one for me"
    }
    should("return message 'One for Alice, one for me' when for name is 'Alice'") {
        // given
        val nome = "Alice"

        // when
        val message = TwoFer().message(nome)

        // then
        message shouldBe "One for Alice, one for me"
    }
    should("return message 'One for you, one for me' when for name is not provided") {
        // given
        val nome = ""

        // when
        val message = TwoFer().message(nome)

        // then
        message shouldBe "One for you, one for me"
    }
    should("return message 'One for Zaphod, one for me' when for name is 'Zaphod'") {
        // given
        val nome = "Zaphod"

        // when
        val message = TwoFer().message(nome)

        // then
        message shouldBe "One for Zaphod, one for me"
    }
})
