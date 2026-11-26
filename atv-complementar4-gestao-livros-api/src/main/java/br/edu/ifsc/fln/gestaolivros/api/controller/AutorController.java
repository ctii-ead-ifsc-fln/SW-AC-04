package br.edu.ifsc.fln.gestaolivros.api.controller;

import br.edu.ifsc.fln.gestaolivros.dto.AutorRequestDTO;
import br.edu.ifsc.fln.gestaolivros.dto.AutorResponseDTO;
import br.edu.ifsc.fln.gestaolivros.exception.RecursoNaoEncontradoException;
import br.edu.ifsc.fln.gestaolivros.exception.RegraNegocioException;
import br.edu.ifsc.fln.gestaolivros.model.domain.Autor;
import br.edu.ifsc.fln.gestaolivros.repository.AutorRepository;
import br.edu.ifsc.fln.gestaolivros.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "Operações de cadastro e consulta de autores")
public class AutorController {

    private final AutorService service;

    @GetMapping
    @Operation(summary = "Listar autores", description = "Retorna todos os autores cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de autores")
    public ResponseEntity<List<AutorResponseDTO>> findAll() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @Operation(summary = "Cadastrar autor", description = "Cadastra um novo autor.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public ResponseEntity<AutorResponseDTO> criarAutor(@RequestBody @Valid AutorRequestDTO autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(autor));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor", description = "Busca um autor pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir autor", description = "Exclui um autor que não esteja associado a livros.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor excluído"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409", description = "Autor associado a livros")
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor", description = "Atualiza os dados de um autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409", description = "E-mail já utilizado")
    })
    public ResponseEntity<AutorResponseDTO> atualizarAutor(@PathVariable Integer id, @RequestBody  @Valid AutorRequestDTO autor) {
        return ResponseEntity.ok(service.atualizar(id, autor));
    }

}
