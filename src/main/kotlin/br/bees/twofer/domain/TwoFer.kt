package br.bees.twofer.domain

class TwoFer : TwoFerInterface{
    override fun message(name: String): String {
        return "One for ${name.ifBlank { "you" }}, one for me"
    }
}
