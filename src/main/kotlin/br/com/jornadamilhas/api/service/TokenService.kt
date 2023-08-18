package br.com.jornadamilhas.api.service

import br.com.jornadamilhas.api.domain.usuario.Usuario
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant


@Service
class TokenService {

    private final val ALGORITMO = Algorithm.HMAC512("secret")
    private final val ISSUER = "API JornadaMilhas"
    private final val DIAS_EXPIRACAO = diasValidade(3)
    fun geraToken(usuario: Usuario) =
        try {
            JWT
                .create()
                .withIssuer(ISSUER)
                .withSubject(usuario.login)
                .withExpiresAt(DIAS_EXPIRACAO)
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