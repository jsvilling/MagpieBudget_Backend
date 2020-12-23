package ch.jvi.magpie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MagpieApplication

fun main(args: Array<String>) {
    runApplication<MagpieApplication>(*args)
}
