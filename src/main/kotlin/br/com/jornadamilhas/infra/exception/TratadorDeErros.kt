package br.com.jornadamilhas.infra.exception

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLIntegrityConstraintViolationException

@RestControllerAdvice
class TratadorDeErros {

    @ExceptionHandler (EntityNotFoundException::class)
    fun tratarErro404() : ResponseEntity<Any> = ResponseEntity.notFound().build()

    @ExceptionHandler (DestinoNaoEncontradoException::class)
    fun tratarDestinoNaoEncontrado() = ResponseEntity
        .badRequest()
        .body(DtoException("Nenhum destino foi encontrado!"))

    @ExceptionHandler(DepoimentoNaoEncontradoException::class)
    fun tratarDepoimentoNaoEncontrado() = ResponseEntity
        .badRequest()
        .body(DtoException("Nenhum depoimento encontrado!"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun tratardirValidacoes(e : MethodArgumentNotValidException): ResponseEntity<MutableMap<String, String>> {
        val erros = mutableMapOf<String, String>()

        val resultados = e.bindingResult
        resultados.fieldErrors.forEach { error ->
            val fieldName = error.field
            val errorMessage = error.defaultMessage ?: "valor inválido"
            erros[fieldName] = errorMessage
        }

        return ResponseEntity.badRequest().body(erros)
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException::class)
    fun tratarError400ParaRegistroDuplicado(ex: SQLIntegrityConstraintViolationException): ResponseEntity<*> {
        val erro = ex.localizedMessage
        val mensagem: String = geraMensagemParaRegistroDuplicado(erro)
        return ResponseEntity.badRequest().body(mensagem)
    }


    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException() = ResponseEntity
        .badRequest()
        .body(DtoException("JSON com formato inválido. Verifique os campos enviados."))

    //talvez outros problemas de integridade caiam aqui, necessitando separar erro da mensagem[0] do campo[-1] e da
    //mensagem padrão
    private fun geraMensagemParaRegistroDuplicado(mensagemDeErro: String): String{
        val mensagem = mensagemDeErro.split(" ")
        val campo = mensagem[mensagem.size-1].replace("'", "").split("\\.")

        return "Campo '${campo[1]}' ja cadastrado!"
    }
}