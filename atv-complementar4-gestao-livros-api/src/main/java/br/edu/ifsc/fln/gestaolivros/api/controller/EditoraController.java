package br.edu.ifsc.fln.gestaolivros.api.controller;

import br.edu.ifsc.fln.gestaolivros.dto.EditoraRequestDTO;
import br.edu.ifsc.fln.gestaolivros.dto.EditoraResponseDTO;
import br.edu.ifsc.fln.gestaolivros.service.EditoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editoras")
@RequiredArgsConstructor
@Tag(name = "Editoras", description = "Operações de cadastro e consulta de editoras")
public class EditoraController {

    private final EditoraService service;

    @GetMapping
    @Operation(summary = "Listar editoras", description = "Retorna todas as editoras cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de editoras")
    public ResponseEntity<List<EditoraResponseDTO>> listarEditoras() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar editora", description = "Busca uma editora pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora encontrada"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    public ResponseEntity<EditoraResponseDTO> getEditora(@PathVariable int id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar editora", description = "Cadastra uma nova editora.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Editora cadastrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public ResponseEntity<EditoraResponseDTO> criarEditora(@RequestBody  @Valid EditoraRequestDTO editora) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(editora));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar editora", description = "Atualiza os dados de uma editora existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada"),
            @ApiResponse(responseCode = "409", description = "E-mail já utilizado")
    })
    public ResponseEntity<EditoraResponseDTO> atualizarEditora(@PathVariable Integer id, @RequestBody  @Valid EditoraRequestDTO editora) {
        return ResponseEntity.ok(service.atualizar(id, editora));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir editora", description = "Exclui uma editora que não esteja associada a livros.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Editora excluída"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada"),
            @ApiResponse(responseCode = "409", description = "Editora associada a livros")
    })
    public ResponseEntity<Void> deletarEditora(@PathVariable Integer id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar editora", description = "Busca todas editoras por nome.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora encontrada"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    public ResponseEntity<List<EditoraResponseDTO>> procurarEditorasPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }
}
