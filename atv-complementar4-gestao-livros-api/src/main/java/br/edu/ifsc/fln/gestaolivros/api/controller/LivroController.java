package br.edu.ifsc.fln.gestaolivros.api.controller;

import br.edu.ifsc.fln.gestaolivros.dto.LivroRequestDTO;
import br.edu.ifsc.fln.gestaolivros.dto.LivroResponseDTO;
import br.edu.ifsc.fln.gestaolivros.model.domain.Livro;
import br.edu.ifsc.fln.gestaolivros.repository.AutorRepository;
import br.edu.ifsc.fln.gestaolivros.repository.EditoraRepository;
import br.edu.ifsc.fln.gestaolivros.repository.LivroRepository;
import br.edu.ifsc.fln.gestaolivros.service.LivroService;
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
@RequestMapping("/livros")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "Operações de cadastro e consulta de livros")
public class LivroController {
    private final LivroService service;

    @GetMapping
    @Operation(summary = "Listar livros", description = "Retorna todos os livros cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de livros")
    public ResponseEntity<List<LivroResponseDTO>> listaLivros() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @Operation(summary = "Cadastrar livro", description = "Cadastra um livro e calcula automaticamente seu preço de venda.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Autor ou editora não encontrado"),
            @ApiResponse(responseCode = "409", description = "ISBN já cadastrado")
    })
    public ResponseEntity<LivroResponseDTO> criarLivro(@RequestBody  @Valid LivroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro", description = "Busca um livro pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroResponseDTO> buscarLivroPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro", description = "Atualiza um livro e recalcula seu preço de venda.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Livro, autor ou editora não encontrado"),
            @ApiResponse(responseCode = "409", description = "ISBN já utilizado")
    })
    public ResponseEntity<LivroResponseDTO> atualizarLivro(@PathVariable Integer id, @RequestBody  @Valid LivroRequestDTO livro) {
        return ResponseEntity.ok(service.atualizar(id, livro));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir livro", description = "Exclui um livro pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro excluído"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
