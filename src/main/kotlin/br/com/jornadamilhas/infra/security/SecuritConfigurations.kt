package br.com.jornadamilhas.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecuritConfigurations(private val tokenFilter: TokenFilter){

    @Bean
    fun filterChain(http: HttpSecurity) : SecurityFilterChain = http
        .csrf { csrf -> csrf.disable() }
        .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .authorizeHttpRequests{ req ->
            req.requestMatchers("/login").permitAll()
            req.requestMatchers("/v3/api-docs/**").permitAll()
            req.requestMatchers("/swagger-ui/**").permitAll()
            req.requestMatchers("/").permitAll()

            req.requestMatchers(HttpMethod.POST,"/destinos/**").hasAnyAuthority("ADMIN")
            req.requestMatchers(HttpMethod.PUT,"/destinos/**").hasAnyAuthority("ADMIN")
            req.requestMatchers(HttpMethod.DELETE,"/destinos/**").hasAnyAuthority("ADMIN")
            req.requestMatchers(HttpMethod.GET,"/destinos/**").permitAll()

            req.requestMatchers(HttpMethod.POST,"/depoimentos/**").hasAnyAuthority("USER")
            req.requestMatchers(HttpMethod.PUT,"/depoimentos/**").hasAnyAuthority("USER")
            req.requestMatchers(HttpMethod.DELETE,"/depoimentos/**").hasAnyAuthority("USER", "ADMIN")
            req.requestMatchers(HttpMethod.GET,"/depoimentos/**").permitAll()
            req.anyRequest().authenticated()
        }
        .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter().javaClass)
        .build()

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration) : AuthenticationManager =
        configuration.authenticationManager

}