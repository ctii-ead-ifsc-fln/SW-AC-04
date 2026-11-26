package br.edu.ifsc.fln.gestaolivros.dto;


import br.edu.ifsc.fln.gestaolivros.model.domain.StatusLivro;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LivroResponseDTO {
    private Integer id;
    private String titulo;
    private String isbn;
    private BigDecimal precoDeCusto;
    private BigDecimal precoDeVenda;
    private BigDecimal margemDeLucro;
    private LocalDate dataDeCadastro;
    private StatusLivro status;
    private Integer autorId;
    private String autorNome;
    private Integer editoraId;
    private String editoraNome;
}
