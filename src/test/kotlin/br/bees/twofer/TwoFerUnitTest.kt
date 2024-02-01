package br.bees.twofer

import br.bees.twofer.domain.TwoFer
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class TwoFerUnitTest : ShouldSpec({
    context("TwoFer Unit Test") {
        forAll(
            row("Bob", "One for Bob, one for me"),
            row("Alice", "One for Alice, one for me"),
            row("Zaphod", "One for Zaphod, one for me"),
            row("", "One for you, one for me")
        ) { name, expected ->
            should("return message '$expected' when for name is '$name'") {
                TwoFer().message(name) shouldBe expected
            }
        }
    }
})
