package br.bees.twofer

import br.bees.twofer.domain.TwoFerInterface
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(TwoFerController::class)
class TwoFerControllerUnitTest(
    private val mockMvc: MockMvc,
    @MockkBean private val twoFer: TwoFerInterface,
) : ShouldSpec({

    should("return message 'One for Bob, one for me' on body and HttpStatus=OK when for name parameter is 'Bob'") {
        every { twoFer.message("Bob") } returns "bolinha, triangulo"

        mockMvc
            .perform(get("/two-fer?name=Bob"))
            .andExpect(status().isOk())
            .andExpect { response ->
                response.response.contentAsString shouldBe "bolinha, triangulo"
            }

        verify { twoFer.message("Bob") }
    }

    should("return message 'One for you, one for me' on body and HttpStatus=OK when for name parameter is empty") {
        every { twoFer.message("") } returns "bolinha, triangulo"

        mockMvc
            .perform(get("/two-fer?name="))
            .andExpect(status().isOk())
            .andExpect { response ->
                response.response.contentAsString shouldBe "bolinha, triangulo"
            }

        verify { twoFer.message("") }
    }
})
