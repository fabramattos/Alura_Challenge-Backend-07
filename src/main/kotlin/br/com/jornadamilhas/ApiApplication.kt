package br.com.jornadamilhas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApplicationApi

fun main(args: Array<String>) {
    runApplication<ApplicationApi>(*args)
}