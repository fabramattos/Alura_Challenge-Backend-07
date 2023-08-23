package br.com.jornadamilhas.api.doc

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenApi(): OpenAPI =
        OpenAPI()
            .components(Components()
                .addSecuritySchemes("bearer-key", SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
            )
            .info(
                Info()
                .title("API Challenge Backend 7ª edição")
                .description(
                    """
                    Api para gerenciar um site de viagens, com banco de dados de depoimentos e destinos.
                    Contem integração com ChatGPT para gerar descrição do destino caso não informado
                    manualmente durante o cadastro.
                    Acesso controlado por token e permissões de usuario.
                    
                    Para login e gerar o token JWT, utilize:
                    
                    usuarios: admin@email.com ou user@email.com
                    
                    senha: 123456
                    
                    Caso a chave da OpenAi não esteja configurada, um texto padrão será gerado.
                    """.trimIndent())
                .contact(
                    Contact()
                    .name("Felipe Mattos")
                    .email("fabramattos@gmail.com"))
            )


}