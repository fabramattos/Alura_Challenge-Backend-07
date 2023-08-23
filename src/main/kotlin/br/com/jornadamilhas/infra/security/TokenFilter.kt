package br.com.jornadamilhas.infra.security

import br.com.jornadamilhas.domain.usuario.UsuarioRepository
import br.com.jornadamilhas.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenFilter(private val tokenService: TokenService,
                  private val repository: UsuarioRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {

        recuperarToken(request)
            ?.let {
                val subject = tokenService.getSubject(it)
                val usuario = repository.findByLogin(subject)
                val tokenAuthentication = UsernamePasswordAuthenticationToken(usuario, null, usuario?.role)
                SecurityContextHolder.getContext().authentication = tokenAuthentication
            }
        filterChain.doFilter(request, response)
    }


    private fun recuperarToken(req: HttpServletRequest): String? {
        val authorizationHeader = req.getHeader("Authorization") ?: return null
        return authorizationHeader.replace("Bearer ", "")
    }

}