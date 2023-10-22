package desafiofinal.controller;

import desafiofinal.dto.ContatoRequestDTO;
import desafiofinal.dto.ContatoResponseDTO;
import desafiofinal.model.Contato;
import desafiofinal.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("contatos")
@Tag(name = "Contato Controller", description = "RESTful API para gerenciar contatos.")
public record ContatoController(ContatoService service) {

    @GetMapping
    @Operation(summary = "Lista todos os contatos com os endereços.", description = "Recupera a " +
            "lista de todos os contatos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
    })
    public Set<ContatoResponseDTO> listarTodos(){
        return service.listarTodos().stream()
                .map(ContatoResponseDTO::new)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera um contato por ID.", description = "Recupera um contato específico " +
            "com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "ID não encontrado.")
    })
    public ContatoResponseDTO buscaPorId(@PathVariable Integer id) {
        var contato = service.pesquisarPorId(id);
        return new ContatoResponseDTO(contato);
    }

    @GetMapping("/busca/{nome}")
    @Operation(summary = "Realiza uma busca por nome.", description = "Recupera uma lista de contato cujo nome" +
            " inicia pelo nome informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizado com sucesso."),
            @ApiResponse(responseCode = "200", description = "Com retorno vazio [] - Nome não encontrado.")
    })
    public Set<ContatoResponseDTO> buscaPorNome(@PathVariable String nome) {
        return service.buscaContatoPorNome(nome).stream()
                .map(ContatoResponseDTO::new)
                .collect(Collectors.toSet());
    }

    @PostMapping
    @Operation(summary = "Cria um novo contato.", description = "Crie um novo contato e retorne os " +
            "dados do contato criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Dados de contato inválidos fornecidos.")
    })
    public ResponseEntity<ContatoResponseDTO> salvar(@RequestBody ContatoRequestDTO contatoPOSTDTO) {
        var contato = service.salvar(new Contato(contatoPOSTDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contato.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ContatoResponseDTO(contato));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um contato", description = "Atualizar os dados de um contato existente " +
            "com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado."),
            @ApiResponse(responseCode = "422", description = "Dados de contato inválidos fornecidos.")
    })
    public ContatoResponseDTO atualizar(@PathVariable Integer id, @RequestBody ContatoRequestDTO contatoPOSTDTO) {
        return new ContatoResponseDTO(service.atualizar(id, new Contato(contatoPOSTDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um contato", description = "Exclui um contato existente com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        service.delete(id);
    }
}
