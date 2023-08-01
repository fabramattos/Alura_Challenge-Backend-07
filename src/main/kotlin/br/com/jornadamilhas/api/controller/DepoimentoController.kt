package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.depoimento.*
import br.com.jornadamilhas.api.service.DepoimentoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
class DepoimentoController(private val service: DepoimentoService) {

    @Transactional
    @PostMapping("/depoimentos")
    fun criar(@RequestBody @Valid dados: DtoCadastroDepoimento,
              uriBuilder: UriComponentsBuilder,
    ): ResponseEntity<DtoDetalhamentoDepoimento> {

        val depoimento = service.salvar(dados)

        val uri = uriBuilder
            .path("depoimentos/{id}")
            .buildAndExpand(depoimento.id)
            .toUri()

        return ResponseEntity
            .created(uri)
            .body(DtoDetalhamentoDepoimento(depoimento))
    }

    @GetMapping("/depoimentos")
    fun exibir(@RequestParam id: Long): ResponseEntity<DtoDetalhamentoDepoimento> {
        val depoimento = service.buscaPorId(id)
        return ResponseEntity.ok(DtoDetalhamentoDepoimento(depoimento))
    }

    @Transactional
    @PutMapping("/depoimentos")
    fun atualiza(@RequestBody @Valid dados: DtoAtualizacaoDepoimento): ResponseEntity<DtoDetalhamentoDepoimento> {
        val depoimento =  service.atualiza(dados)

        return ResponseEntity.ok(DtoDetalhamentoDepoimento(depoimento))
    }

    @Transactional
    @DeleteMapping("/depoimentos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@RequestParam id: Long) = service.deletar(id)


    @GetMapping("/depoimentos-home")
    fun home(): ResponseEntity<List<DtoDetalhamentoDepoimento>> {
        val depoimentos = service
            .escolheTresAleatorios()
            .map { DtoDetalhamentoDepoimento(it) }

        return ResponseEntity.ok(depoimentos)
    }


}