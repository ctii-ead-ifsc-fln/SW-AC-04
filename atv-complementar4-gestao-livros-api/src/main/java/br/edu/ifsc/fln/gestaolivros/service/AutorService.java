package br.edu.ifsc.fln.gestaolivros.service;


import br.edu.ifsc.fln.gestaolivros.dto.AutorRequestDTO;
import br.edu.ifsc.fln.gestaolivros.dto.AutorResponseDTO;
import br.edu.ifsc.fln.gestaolivros.exception.RecursoNaoEncontradoException;
import br.edu.ifsc.fln.gestaolivros.exception.RegraNegocioException;
import br.edu.ifsc.fln.gestaolivros.model.domain.Autor;
import br.edu.ifsc.fln.gestaolivros.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AutorService {
    private final AutorRepository repository;
    private final ModelMapper modelMapper;

    public List<AutorResponseDTO> listar() {
//        List<AutorResponseDTO> listaResponseDTO = new ArrayList<>();
//        for (Autor autor : repository.findAll()) {
//            AutorResponseDTO autorResponseDTO = new AutorResponseDTO();
//            autorResponseDTO.setId(autor.getId());
//            autorResponseDTO.setNome(autor.getNome());
//            autorResponseDTO.setEmail(autor.getEmail());
//            autorResponseDTO.setNacionalidade(autor.getNacionalidade());
//            listaResponseDTO.add(autorResponseDTO);
//        }
        return repository.findAll().stream()
                .map( a -> modelMapper.map(a, AutorResponseDTO.class)).toList();
    }

    public AutorResponseDTO criar(AutorRequestDTO autor) {
        if (repository.existsByEmail(autor.getEmail())) {
            throw new RegraNegocioException("Já existe um autor cadastrado com esse e-mail");
        }
        Autor autorCriado = modelMapper.map(autor, Autor.class);
        return modelMapper.map(repository.save(autorCriado), AutorResponseDTO.class);
//        Autor a = new Autor();
//        a.setNome(autor.getNome());
//        a.setEmail(autor.getEmail());
//        a.setNacionalidade(autor.getNacionalidade());
//        a = repository.save(a);
//        AutorResponseDTO autorResponseDTO = new AutorResponseDTO();
//        autorResponseDTO.setId(a.getId());
//        autorResponseDTO.setNome(autor.getNome());
//        autorResponseDTO.setEmail(autor.getEmail());
//        autorResponseDTO.setNacionalidade(autor.getNacionalidade());
//        return autorResponseDTO;
    }

    public AutorResponseDTO buscar(Integer id) {
        return modelMapper.map(obter(id), AutorResponseDTO.class);
    }

    public void deletar(Integer id) {
        Autor autor = obter(id);
        repository.delete(autor);
    }

    private Autor obter(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Autor não encontrado: " + id));
    }

    public AutorResponseDTO atualizar(Integer id, AutorRequestDTO autor) {
        Autor autorAtual = obter(id);
        if (repository.existsByEmailAndIdNot(autor.getEmail(), id)) {
            throw new RegraNegocioException("Outro autor já utiliza este e-mail");
        }
        autorAtual.setNome(autor.getNome());
        autorAtual.setEmail(autor.getEmail());
        autorAtual.setNacionalidade(autor.getNacionalidade());
        return modelMapper.map(repository.save(autorAtual), AutorResponseDTO.class);
    }
}
