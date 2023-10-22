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
    @Operation(summary = "Lista todos os contatos com os endereços.", description = "Recupera a lista de todos os contatos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
    })
    public Set<ContatoResponseDTO> listarTodosContatos(){
        return service.listarTodos().stream()
                .map(ContatoResponseDTO::new)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera um usuário por ID.", description = "Recupera um usuário específico com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado.")
    })
    public ContatoResponseDTO findById(@PathVariable Integer id) {
        var contato = service.pesquisarPorId(id);
        return new ContatoResponseDTO(contato);
    }

    @PostMapping
    @Operation(summary = "Cria um novo contato.", description = "Crie um novo contato e retorne os dados do contato criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Dados de contato inválidos fornecidos.")
    })
    public ResponseEntity<ContatoResponseDTO> create(@RequestBody ContatoRequestDTO contatoRequestDto) {
        var contato = service.salvar(new Contato(contatoRequestDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contato.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ContatoResponseDTO(contato));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um contato", description = "Atualizar os dados de um contato existente com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado."),
            @ApiResponse(responseCode = "422", description = "Dados de contato inválidos fornecidos.")
    })
    public ContatoResponseDTO update(@PathVariable Integer id, @RequestBody ContatoRequestDTO contatoRequestDTO) {
        return new ContatoResponseDTO(service.atualizar(id, new Contato(contatoRequestDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um contato", description = "Exclui um contato existente com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
