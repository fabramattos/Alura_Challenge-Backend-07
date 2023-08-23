package br.com.jornadamilhas.controller

import br.com.jornadamilhas.domain.destino.*
import br.com.jornadamilhas.service.DestinoService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/destinos")
@SecurityRequirement(name = "bearer-key")
class DestinoController(private val service : DestinoService) {

    @Transactional
    @PostMapping
    fun criar(@RequestBody @Valid dados : DtoCadastroDestino,
              uriBuilder: UriComponentsBuilder): ResponseEntity<DtoDetalhamentoDestino> {

        val destino = service.save(dados)

        val uri = uriBuilder
            .path("destinos/{id}")
            .buildAndExpand(destino.id)
            .toUri()

        return ResponseEntity
            .created(uri)
            .body(DtoDetalhamentoDestino(destino))
    }

    @GetMapping("/busca")
    fun buscar(@RequestParam nome: String) : ResponseEntity<List<DtoDetalhamentoHomeDestinos>>{
        val destinos = service
            .buscaPorNome(nome)
            .map { DtoDetalhamentoHomeDestinos(it) }

        return ResponseEntity.ok(destinos)
    }

    @GetMapping
    fun listarHome(@PageableDefault(size = 6, sort = ["nome"] ) paginacao : Pageable) : ResponseEntity<Any>{
        val destinos = service
            .busca(paginacao)
            .map { DtoDetalhamentoHomeDestinos(it) }

        return ResponseEntity.ok(destinos)
    }

    @GetMapping("/{id}")
    fun exibir(@PathVariable id: Long): ResponseEntity<DtoDetalhamentoIndividualDestino> {
        val destino = service.buscaPorId(id)

        return ResponseEntity.ok(DtoDetalhamentoIndividualDestino(destino))
    }

    @Transactional
    @PutMapping
    fun atualiza(@RequestBody @Valid dados: DtoAtualizacaoDestino) : ResponseEntity<DtoDetalhamentoDestino>{
        val destino = service.atualiza(dados)
        return ResponseEntity.ok(DtoDetalhamentoDestino(destino))
    }

    @Transactional
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@RequestParam id : Long) = service.delete(id)

}