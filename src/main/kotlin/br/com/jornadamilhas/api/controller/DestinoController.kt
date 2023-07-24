package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.depoimento.*
import br.com.jornadamilhas.api.domain.destino.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("destinos")
class DestinoController(private val repository: DestinoRepository) {

    @Transactional
    @PostMapping()
    fun criar(@RequestBody @Valid dados : DtoCadastroDestino, uriBuilder: UriComponentsBuilder): ResponseEntity<DtoDetalhamentoDestino> {
        val destino = Destino(dados)
        repository.save(destino)

        val uri = uriBuilder
            .path("destinos/{id}")
            .buildAndExpand(destino.id)
            .toUri()

        return ResponseEntity
            .created(uri)
            .body(DtoDetalhamentoDestino(destino))
    }

    @GetMapping
    fun exibir(@RequestParam nome: String) : ResponseEntity<Any>{
        val destinos = repository
            .findAllByNome(nome)
            .map {DtoDetalhamentoDestino(it)}

        if(destinos.isEmpty())
            return ResponseEntity.badRequest().body(DtoMensagemErro("Nenhum destino foi encontrado!"))
        return ResponseEntity.ok(destinos)
    }

    @Transactional
    @PutMapping
    fun atualiza(@RequestBody @Valid dados: DtoAtualizacaoDestino) : ResponseEntity<DtoDetalhamentoDestino>{
        val destino = repository.getReferenceById(dados.id)
        destino.atualiza(dados)
        return ResponseEntity.ok(DtoDetalhamentoDestino(destino))
    }
    @Transactional
    @DeleteMapping
    fun delete(@RequestParam id : Long) : ResponseEntity<Any>{
        repository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}