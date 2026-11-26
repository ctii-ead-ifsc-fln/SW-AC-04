package br.edu.ifsc.fln.gestaolivros.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Autor {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Nome do autor é obrigatório.")
    @Size(max = 60, message = "O nome não pode ter mais de 60 caracteres.")
    private String nome;
    @NotBlank(message = "E-mail é obrigatório.")
    @Size(max = 200, message = "O e-mail não pode ter mais de 200 caracteres.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;
    @Size(max = 20, message = "A nacionalidade não pode ter mais de 20 caracteres.")
    private String nacionalidade;

    @Override
    public String toString() {
        return nome;
    }
}
