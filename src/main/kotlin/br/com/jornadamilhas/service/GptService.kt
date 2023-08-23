package br.com.jornadamilhas.service

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.chatCompletionRequest
import com.aallam.openai.api.exception.AuthenticationException
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GptService(
    @Value("\${gpt.secret}")
    private val gptKey: String,
    private val openAI: OpenAI = OpenAI(token = gptKey, logging = LoggingConfig(LogLevel.All))) {

    fun geraDescricao(pais: String): String {
        if(gptKey.equals("123456"))
            return geraDescricaoLorem()

        val descricao: String?
        try {
            descricao = chatGpt(pais)
        } catch (e: AuthenticationException) {
            return geraDescricaoLorem()
        }

        if (descricao.isNullOrEmpty())
            return geraDescricaoLorem()

        return descricao
    }

    private fun geraDescricaoLorem(): String {
        var textoLorem = ""
        for (i in 1..30) {
            textoLorem += "Lorem Ipsum "
        }
        textoLorem = textoLorem.substring(0, 95) + "..."

        var textoFinal = textoLorem
        textoFinal += System.lineSeparator() + System.lineSeparator()
        textoFinal += textoLorem

        return textoFinal
    }

    @OptIn(BetaOpenAI::class)
    private fun chatGpt(pais: String) = runBlocking{
        val chatMessages = mutableListOf(
            ChatMessage(
                role = ChatRole.User,
                content = " Faça um resumo sobre $pais. " +
                        "O resumo deve conter 2 parágrafos. Cada parágrafo com no máximo 100 caracteres. " +
                        "Enfatize o porque o local merece uma visita (comida, paisagens, pontos turisticos, etc). " +
                        "Utilize uma linguagem informal."
            )
        )

        val request = chatCompletionRequest {
            model = ModelId("gpt-3.5-turbo")
            messages = chatMessages
        }

        val response = openAI.chatCompletion(request)
        val message = response.choices.first().message

        return@runBlocking message?.content
    }

}
