package br.bees.twofer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TwoferApplication

fun main(args: Array<String>) {
	runApplication<TwoferApplication>(*args)
}
