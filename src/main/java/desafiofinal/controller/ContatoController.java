package desafiofinal.controller;

import desafiofinal.dto.ContatoPostDTO;
import desafiofinal.dto.ContatoResponseDTO;
import desafiofinal.model.Contato;
import desafiofinal.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("contatos")
@Tag(name = "Contato Controller", description = "RESTful API for managing users.")
public record ContatoController(ContatoService service) {

    @GetMapping
    @Operation(summary = "Lista todos os contatos com os endereços", description = "Recupera a lista de todos os contatos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public Set<ContatoResponseDTO> listarTodosContatos(){
        return service.listarTodos().stream()
                .map(ContatoResponseDTO::new)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Retrieve a specific user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ContatoResponseDTO findById(@PathVariable Integer id) {
        var contato = service.pesquisarPorId(id);
        return new ContatoResponseDTO(contato);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<ContatoResponseDTO> create(@RequestBody ContatoPostDTO contatoPostDto) {
        var contato = service.salvar(new Contato(contatoPostDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contato.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ContatoResponseDTO(contato));
    }



}
