package br.com.jornadamilhas.api.infra.exception

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException::class)
    fun tratarErro404() : ResponseEntity<Any> = ResponseEntity.notFound().build()

}