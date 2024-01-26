package br.bees.twofer

import br.bees.twofer.domain.TwoFerInterface
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/two-fer")
class TwoFerController(
    private val twoFer: TwoFerInterface
) {
    @GetMapping
    fun message(
        @RequestParam("name") name: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(twoFer.message(name))
    }
}
