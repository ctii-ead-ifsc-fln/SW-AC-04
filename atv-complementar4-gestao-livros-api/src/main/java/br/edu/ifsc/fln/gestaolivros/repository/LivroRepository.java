package br.edu.ifsc.fln.gestaolivros.repository;

import br.edu.ifsc.fln.gestaolivros.model.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Integer id);
}
