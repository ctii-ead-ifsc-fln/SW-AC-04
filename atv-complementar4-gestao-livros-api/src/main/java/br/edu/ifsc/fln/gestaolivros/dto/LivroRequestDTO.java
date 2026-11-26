package br.edu.ifsc.fln.gestaolivros.dto;


import br.edu.ifsc.fln.gestaolivros.model.domain.StatusLivro;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LivroRequestDTO {
    @NotBlank(message = "O titulo do livro é obrigatório.")
    @Size(max=100, message = "O título não pode ter mais de 100 caracteres")
    private String titulo;

    @NotBlank(message = "O ISBN é obrigatório.")
    @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres.")
    private String isbn;

    @NotNull(message = "O preço de custo é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço de custo deve ser maior que zero.")
    @Digits(integer = 10, fraction = 2, message = "O preço de custo deve ter até 10 dígitos inteiros e 2 decimais.")
    private BigDecimal precoDeCusto;

    @NotNull(message = "A margem de lucro é obrigatória.")
    @DecimalMin(value = "0.00", message = "A margem de lucro não pode ser negativa.")
    @Digits(integer = 4, fraction = 2, message = "A margem deve ter até 4 dígitos inteiros e 2 decimais.")
    private BigDecimal margemDeLucro;

    @NotNull(message = "A data de cadastro é obrigatória.")
    @PastOrPresent(message = "A data de cadastro não pode ser futura.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeCadastro;

    @NotNull(message = "O status do livro é obrigatório.")
    private StatusLivro status;

    @NotNull(message = "O identificador do autor é obrigatório.")
    @Positive(message = "O identificador do autor deve ser positivo.")
    private Integer autorId;

    @NotNull(message = "O identificador da editora é obrigatório.")
    @Positive(message = "O identificador da editora deve ser positivo.")
    private Integer editoraId;
}
