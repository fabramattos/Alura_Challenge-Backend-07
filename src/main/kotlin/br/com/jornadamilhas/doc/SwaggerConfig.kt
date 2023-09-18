package br.com.jornadamilhas.doc

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
                    ||========================================================================================||
                    ||   Api para gerenciar dados de um site de viagens contendo depoimentos e destinos.      ||
                    ||   Integração com ChatGPT: gera campo 'descrição' do destino caso não informado         ||
                    ||   manualmente durante o cadastro. Caso não tenha sido configurada a chave de acesso    ||
                    ||   da openAI, gera um texto padrão.                                                     ||
                    ||                                                                                        ||
                    ||   Acesso controlado por token e permissões de usuario.                                 ||
                    ||==========================================================================================
                      
                                        
                    ||=======================================================||
                    ||                                                       ||       
                    ||       Para login e gerar o token JWT, utilize:        ||
                    ||                                                       ||
                    ||       usuario: admin@email.com | senha: 123456        ||
                    ||       usuario: user@email.com  | senha: 123456        ||
                    ||                                                       ||
                    ||=======================================================||
                    """)
                .contact(
                    Contact()
                    .name("Felipe Mattos")
                    .email("fabramattos@gmail.com"))
            )


}