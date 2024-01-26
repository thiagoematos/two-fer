package br.bees.twofer

import br.bees.twofer.domain.TwoFer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TwoFerJunitTest {

    @Test
    fun `deve retornar "One for Bob, one for me" quando o nome for Bob`() {
        // given
        val nome = "Bob"

        // when
        val message = TwoFer().message(nome)

        // then
        assertThat(message).isEqualTo("One for Bob, one for me")
    }

    @Test
    fun `deve retornar "One for Alice, one for me" quando o nome for Alice`() {
        // given
        val nome = "Alice"

        // when
        val message = TwoFer().message(nome)

        // then
        assertThat(message).isEqualTo("One for Alice, one for me")
    }

    @Test
    fun `deve retornar "One for you, one for me" quando não tiver inputado um nome`() {
        // given
        val nome = ""

        // when
        val message = TwoFer().message(nome)

        // then
        assertThat(message).isEqualTo("One for you, one for me")
    }

    @Test
    fun `deve retornar "One for Zaphod, one for me" quando o nome for Zaphod`() {
        // given
        val nome = "Zaphod"

        // when
        val message = TwoFer().message(nome)

        // then
        assertThat(message).isEqualTo("One for Zaphod, one for me")
    }
}
