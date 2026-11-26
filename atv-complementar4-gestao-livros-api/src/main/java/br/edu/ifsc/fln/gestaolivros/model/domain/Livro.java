package br.edu.ifsc.fln.gestaolivros.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Livro {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "O titulo do livro é obrigatório.")
    @Size(max=100, message = "O título não pode ter mais de 100 caracteres")
    private String titulo;
    @Size(max=20, message = "O ISBN não pode ter mais de 20 caracteres")
    private String isbn;
    @NotNull(message = "O preço do livro não pode ser nulo")
    private BigDecimal precoDeCusto;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private BigDecimal precoDeVenda;
    @NotNull(message = "A margem de lucro do livro é obrigatória.")
    private BigDecimal margemDeLucro;
    @NotNull(message = "A data de cadastro precisa ser informada.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeCadastro;

    @NotNull(message = "Informe o status do livro")
    @Enumerated(EnumType.STRING)
    private StatusLivro status;

    @NotNull(message = "O autor do livro é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @NotNull(message = "A editora do livro é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editora editora;

    public BigDecimal getPrecoDeVenda() {
        calcularPrecoDeVenda();
        return precoDeVenda;
    }

    public void calcularPrecoDeVenda() {
        this.precoDeVenda = this.precoDeCusto.add(this.precoDeCusto.multiply(this.margemDeLucro.divide(BigDecimal.valueOf(100))));
    }

    @Override
    public String toString() {
        return titulo;
    }
}
