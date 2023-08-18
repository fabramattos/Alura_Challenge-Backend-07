package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.destino.*
import br.com.jornadamilhas.api.infra.exception.DtoException
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DestinoControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired @MockBean private val repository: DestinoRepository,
    @Autowired private val jacksonDtoCadastro: JacksonTester<DtoCadastroDestino>,
    @Autowired private val jacksonDtoDetalhamentoCompleto: JacksonTester<DtoDetalhamentoDestino>,
    @Autowired private val jacksonListDtoHome: JacksonTester<List<DtoDetalhamentoHomeDestinos>>,
    @Autowired private val jacksonDtoAtualizacao: JacksonTester<DtoAtualizacaoDestino>,
    @Autowired private val jacksonDtoException: JacksonTester<DtoException>,

    @Autowired private val jacksonDtoIndividualDestino: JacksonTester<DtoDetalhamentoIndividualDestino>
){
    private final val DTO_EXCEPTION = DtoException("Nenhum destino foi encontrado!")

    private final val DTO_CADASTRO_DESTINO = DtoCadastroDestino(
        foto1Url = "http://www.urlFoto.com.br",
        foto2Url = "http://www.urlFoto2.com.br",
        nome = "Brasil",
        meta = "Meta text",
        descricao = "descrição do destino",
        preco = BigDecimal("300.00"))

    private final val DTO_ATUALIZACAO_DESTINO = DtoAtualizacaoDestino(
        id = 1L,
        preco = BigDecimal("900.00"))


    @Test
    @DisplayName("Dado um json válido, Quando enviar um Post, Então deveria retornar 201 e o json detalhado")
    fun criar1() {

        val destino = Destino(DTO_CADASTRO_DESTINO)
        val jsonEsperado = jacksonDtoDetalhamentoCompleto.write(DtoDetalhamentoDestino(destino)).json

        Mockito
            .`when`(repository.save(Mockito.any()))
            .thenReturn(destino)

        mockMvc
            .perform(
                post("/destinos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoCadastro.write(DTO_CADASTRO_DESTINO).json))
            .andExpect(status().isCreated)
            .andExpect(content().json(jsonEsperado))
    }


    @Test
    @DisplayName("Dado no campo 'busca' um nome existente, Quando solicitado via GET, Então deveria retornar 200 todos resultados")
    fun exibir1() {

        val destinos = listaDestinosBrasil()
        val dtoDestinos = destinos.map { destino -> DtoDetalhamentoHomeDestinos(destino) }
        val jsonDestinos = jacksonListDtoHome.write(dtoDestinos).json

        Mockito
            .`when`(repository.findAllByNome(Mockito.anyString()))
            .thenReturn(Optional.of(destinos))

        mockMvc
            .perform(
                get("/destinos/busca")
                    .param("nome", "Brasil"))
            .andExpect(status().isOk)
            .andExpect(content().json(jsonDestinos))
    }

    @Test
    @DisplayName("Dado no campo 'busca' um nome inexistente, Quando solicitado via GET, Então deveria retornar 400")
    fun exibir2() {
        mockMvc
            .perform(
                get("/destinos/busca")
                    .param("nome", "Brasil"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("Dado na url um id existente, Quando solicitado via GET, Então deveria retornar codigo 200 os detalhes do Destino")
    fun exibir3() {
        val idBusca = 1L
        val destino = Destino(DTO_CADASTRO_DESTINO)
        val jsonEsperado = jacksonDtoIndividualDestino.write(DtoDetalhamentoIndividualDestino(destino)).json

        Mockito
            .`when`(repository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(destino))

        mockMvc
            .perform(
                get("/destinos/$idBusca"))
            .andExpect(status().isOk)
            .andExpect(content().json(jsonEsperado))
    }


    @Test
    @DisplayName("Dado na url um id inexistente, Quando solicitado via GET, Então deveria retornar 400")
    fun exibir4() {
        val JSON_EXCEPTION = jacksonDtoException.write(DTO_EXCEPTION).json
        val idBusca = 1L
        mockMvc
            .perform(
                get("/destinos/$idBusca")
                    .param("nome", "Brasil"))
            .andExpect(status().isBadRequest)
            .andExpect(content().json(JSON_EXCEPTION))
    }

    @Test
    @DisplayName("Dado um JSON válido, Quando tentar atualizar um destino, Deve retornar HTTP 200 e o DTO do destino atualizado")
    fun atualiza1() {
        val destino = Destino(DTO_CADASTRO_DESTINO)

        Mockito
            .`when`(repository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(destino))

        val dtoDetalhamento = DtoDetalhamentoDestino(
            destino.id,
            destino.foto1Url,
            destino.foto2Url,
            destino.nome,
            destino.meta,
            destino.descricao,
            BigDecimal("900.00")
        )

        val jsonEsperado = jacksonDtoDetalhamentoCompleto.write(dtoDetalhamento).json

        mockMvc
            .perform(put("/destinos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonDtoAtualizacao.write(DTO_ATUALIZACAO_DESTINO).json))
            .andExpect(status().isOk)
            .andExpect(content().json(jsonEsperado))
    }

    @Test
    @DisplayName("Dado um JSON inválido, Quando tentar atualizar um destino, Deve retornar HTTP 400")
    fun atualiza2() {
        mockMvc
            .perform(
                put("/destinos")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("Dado um JSON válido com id inválido, Quando tentar atualizar um destino, Deve devolver HTTP 404")
    fun atualiza3() {
        val JSON_EXCEPTION = jacksonDtoException.write(DTO_EXCEPTION).json

        Mockito
            .`when`(repository.getReferenceById(Mockito.anyLong()))
            .thenThrow(EntityNotFoundException()) // simulando que o ID lançará erro de entidade não encontrada

        mockMvc
            .perform(
                put("/destinos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoAtualizacao.write(DTO_ATUALIZACAO_DESTINO).json)) //json válido
            .andExpect(status().isBadRequest)
            .andExpect(content().json(JSON_EXCEPTION))
    }

    @Test
    @DisplayName("Dado um id válido, Quando tentar excluir um destino, Deve retornar HTTP 204")
    fun delete1() {
        val destino = Destino(DTO_CADASTRO_DESTINO)

        Mockito
            .`when`(repository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(destino))

        Mockito
            .doNothing()
            .`when`(repository).delete(destino)

        mockMvc
            .perform(delete("/destinos").param("id", "1"))
            .andExpect(status().isNoContent)
    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar excluir um destino, Deve retornar 400")
    fun delete2() {
        val JSON_EXCEPTION = jacksonDtoException.write(DTO_EXCEPTION).json

        mockMvc
            .perform(delete("/destinos").param("id", "1"))
            .andExpect(status().isBadRequest)
            .andExpect(content().json(JSON_EXCEPTION))
    }

    fun listaDestinosBrasil() = listOf<Destino>(
        Destino(DtoCadastroDestino("foto1Url.com.br", "foto2Url.com.br", "Brasil", "meta", "descrição", BigDecimal("100.00"))),
        Destino(DtoCadastroDestino("foto1Url.com.br", "foto2Url.com.br", "Brasil", "meta", "descrição", BigDecimal("200.00"))),
        Destino(DtoCadastroDestino("foto1Url.com.br", "foto2Url.com.br", "Brasil", "meta", "descrição", BigDecimal("300.00"))),
        Destino(DtoCadastroDestino("foto1Url.com.br", "foto2Url.com.br", "Brasil", "meta", "descrição", BigDecimal("400.00"))),
        Destino(DtoCadastroDestino("foto1Url.com.br", "foto2Url.com.br", "Brasil", "meta", "descrição", BigDecimal("500.00"))),
        Destino(DtoCadastroDestino("foto1Url.com.br", "foto2Url.com.br", "Brasil", "meta", "descrição", BigDecimal("600.00"))),
        Destino(DtoCadastroDestino("foto1Url.com.br", "foto2Url.com.br", "Brasil", "meta", "descrição", BigDecimal("700.00"))),
    )
}