package br.com.jornadamilhas.api.service

import br.com.jornadamilhas.api.domain.usuario.Usuario
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant


@Service
class TokenService(
    @Value("\${jwt.secret}")
    private val JWT_SECRET: String,
    private val ALGORITMO: Algorithm = Algorithm.HMAC512(JWT_SECRET),
    private val ISSUER: String = "API JornadaMilhas",
    private val DIAS_EXPIRACAO: Long = 3
) {
    fun geraToken(usuario: Usuario) =
        try {
            JWT
                .create()
                .withIssuer(ISSUER)
                .withSubject(usuario.login)
                .withExpiresAt(diasValidade(DIAS_EXPIRACAO))
                .sign(ALGORITMO)
        } catch (e: JWTCreationException) {
            throw RuntimeException("Erro ao gerar jwt")
        }

    fun getSubject(token: String) : String =
        try {
            JWT
                .require(ALGORITMO)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .subject
        } catch (e: JWTVerificationException) {
            throw RuntimeException("JWT inválido ou expirado")
        }

    private final fun diasValidade(dias: Long) : Instant {
        val duration = Duration.ofDays(dias).toSeconds()
        return Instant.now().plusSeconds(duration)
    }
}