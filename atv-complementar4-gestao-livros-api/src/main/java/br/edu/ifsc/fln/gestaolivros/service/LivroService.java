package br.edu.ifsc.fln.gestaolivros.service;


import br.edu.ifsc.fln.gestaolivros.dto.LivroRequestDTO;
import br.edu.ifsc.fln.gestaolivros.dto.LivroResponseDTO;
import br.edu.ifsc.fln.gestaolivros.exception.RecursoNaoEncontradoException;
import br.edu.ifsc.fln.gestaolivros.exception.RegraNegocioException;
import br.edu.ifsc.fln.gestaolivros.model.domain.Autor;
import br.edu.ifsc.fln.gestaolivros.model.domain.Editora;
import br.edu.ifsc.fln.gestaolivros.model.domain.Livro;
import br.edu.ifsc.fln.gestaolivros.repository.AutorRepository;
import br.edu.ifsc.fln.gestaolivros.repository.EditoraRepository;
import br.edu.ifsc.fln.gestaolivros.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final ModelMapper mapper;

    public LivroResponseDTO criar(LivroRequestDTO dto) {
        if (livroRepository.existsByIsbn(dto.getIsbn()))
            throw new RegraNegocioException("Já existe um livro cadastrado com este ISBN.");

        Livro livro = new Livro();
        preencher(livro, dto);
        livro.calcularPrecoDeVenda();
        return converter(livroRepository.save(livro));
    }

    @Transactional(readOnly = true)
    public List<LivroResponseDTO> listar() {
        return livroRepository.findAll().stream().map(this::converter).toList();
    }

    @Transactional(readOnly = true)
    public LivroResponseDTO buscar(Integer id) {
        return converter(obter(id));
    }

    public LivroResponseDTO atualizar(Integer id, LivroRequestDTO dto) {
        Livro livro = obter(id);
        if (livroRepository.existsByIsbnAndIdNot(dto.getIsbn(), id))
            throw new RegraNegocioException("Outro livro já utiliza este ISBN.");
        preencher(livro, dto);
        livro.calcularPrecoDeVenda();
        return converter(livroRepository.save(livro));
    }

    public void excluir(Integer id) {
        livroRepository.delete(obter(id));
    }

    private void preencher(Livro livro, LivroRequestDTO dto) {
        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setPrecoDeCusto(dto.getPrecoDeCusto());
        livro.setMargemDeLucro(dto.getMargemDeLucro());
        livro.setDataDeCadastro(dto.getDataDeCadastro());
        livro.setStatus(dto.getStatus());

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Autor não encontrado: " + dto.getAutorId()));
        Editora editora = editoraRepository.findById(dto.getEditoraId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Editora não encontrada: " + dto.getEditoraId()));
        livro.setAutor(autor);
        livro.setEditora(editora);
    }

    private Livro obter(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado: " + id));
    }

    private LivroResponseDTO converter(Livro livro) {
        LivroResponseDTO dto = mapper.map(livro, LivroResponseDTO.class);
        dto.setAutorId(livro.getAutor().getId());
        dto.setAutorNome(livro.getAutor().getNome());
        dto.setEditoraId(livro.getEditora().getId());
        dto.setEditoraNome(livro.getEditora().getNome());
        return dto;
    }
}
