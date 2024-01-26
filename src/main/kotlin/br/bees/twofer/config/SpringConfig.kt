package br.bees.twofer.config

import br.bees.twofer.domain.TwoFer
import br.bees.twofer.domain.TwoFerInterface
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig {
    @Bean
    fun twoFer(): TwoFerInterface {
        return TwoFer()
    }
}
