package br.edu.ifsc.fln.gestaolivros.service;

import br.edu.ifsc.fln.gestaolivros.dto.EditoraRequestDTO;
import br.edu.ifsc.fln.gestaolivros.dto.EditoraResponseDTO;
import br.edu.ifsc.fln.gestaolivros.exception.RecursoNaoEncontradoException;
import br.edu.ifsc.fln.gestaolivros.exception.RegraNegocioException;
import br.edu.ifsc.fln.gestaolivros.model.domain.Editora;
import br.edu.ifsc.fln.gestaolivros.repository.EditoraRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EditoraService {
    private final EditoraRepository repository;
    private final ModelMapper mapper;

    public EditoraResponseDTO criar(EditoraRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail()))
            throw new RegraNegocioException("Já existe uma editora cadastrada com este e-mail.");
        return mapper.map(repository.save(mapper.map(dto, Editora.class)), EditoraResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<EditoraResponseDTO> listar() {
        return repository.findAll().stream()
                .map(e -> mapper.map(e, EditoraResponseDTO.class)).toList();
    }

    @Transactional(readOnly = true)
    public List<EditoraResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContaining(nome).stream()
                .map(e -> mapper.map(e, EditoraResponseDTO.class)).toList();
    }

    @Transactional(readOnly = true)
    public EditoraResponseDTO buscar(Integer id) {
        return mapper.map(obter(id), EditoraResponseDTO.class);
    }

    public EditoraResponseDTO atualizar(Integer id, EditoraRequestDTO dto) {
        Editora editora = obter(id);
        if (repository.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new RegraNegocioException("Outra editora já utiliza este e-mail.");
        editora.setNome(dto.getNome());
        editora.setEmail(dto.getEmail());
        return mapper.map(repository.save(editora), EditoraResponseDTO.class);
    }

    public void excluir(Integer id) {
        repository.delete(obter(id));
    }

    private Editora obter(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Editora não encontrada: " + id));
    }
}
