package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.depoimento.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
class DepoimentoController(private val repository: DepoimentoRepository) {

    @Transactional
    @PostMapping("/depoimentos")
    fun criar(@RequestBody @Valid dados : DtoCadastroDepoimento, uriBuilder: UriComponentsBuilder): ResponseEntity<DtoDetalhamentoDepoimento> {
        val depoimento = Depoimento(dados)
        repository.save(depoimento)

        val uri = uriBuilder
            .path("depoimentos/{id}")
            .buildAndExpand(depoimento.id)
            .toUri()

        return ResponseEntity
            .created(uri)
            .body(DtoDetalhamentoDepoimento(depoimento))
    }

    @GetMapping("/depoimentos")
    fun exibir(@RequestParam id: Long) : ResponseEntity<DtoDetalhamentoDepoimento>{
        val depoimento = repository.getReferenceById(id)
        return ResponseEntity.ok(DtoDetalhamentoDepoimento(depoimento))
    }

    @Transactional
    @PutMapping("/depoimentos")
    fun atualiza(@RequestBody @Valid dados: DtoAtualizacaoDepoimento) : ResponseEntity<DtoDetalhamentoDepoimento>{
        val depoimento = repository.getReferenceById(dados.id)
        depoimento.atualizarInformacoes(dados)
        return ResponseEntity.ok(DtoDetalhamentoDepoimento(depoimento))
    }
    @Transactional
    @DeleteMapping("/depoimentos")
    fun delete(@RequestParam id : Long) : ResponseEntity<Any>{
        repository.deleteById(id)
        return ResponseEntity.noContent().build()
    }


    @GetMapping("/depoimentos-home")
    fun home() : ResponseEntity<List<DtoDetalhamentoDepoimento>>{
        val listaDto = DepoimentoService(repository)
            .escolheTresAleatorios()
            .map { depoimento -> DtoDetalhamentoDepoimento(depoimento) }

        return ResponseEntity.ok(listaDto)
    }






}