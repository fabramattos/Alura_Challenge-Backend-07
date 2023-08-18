package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.usuario.DtoUsuarioLogin
import br.com.jornadamilhas.api.domain.usuario.Usuario
import br.com.jornadamilhas.api.service.TokenService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController (private val manager: AuthenticationManager,
                       private val tokenService: TokenService){

    @PostMapping
    fun login(@RequestBody @Valid form: DtoUsuarioLogin): ResponseEntity<String> {
        val login = UsernamePasswordAuthenticationToken(form.login, form.senha)
        val authentication = manager.authenticate(login)
        val tokenJWT = tokenService.geraToken(authentication.principal as Usuario)

        return ResponseEntity.ok(tokenJWT)
    }

}