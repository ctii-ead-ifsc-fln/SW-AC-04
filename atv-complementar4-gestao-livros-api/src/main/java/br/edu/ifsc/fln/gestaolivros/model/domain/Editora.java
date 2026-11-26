package br.edu.ifsc.fln.gestaolivros.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Editora {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "O nome da editora é obrigatório.")
    @Size(max=60, message = "O nome não pode ter mais de 60 caracteres")
    private String nome;
    @NotBlank(message = "O email da editora é obrigatório.")
    @Size(max=200, message = "O email não pode ter mais de 200 caracteres")
    @Email(message = "Formato inválido do e-mail.")
    private String email;
    @NotBlank(message = "A cidade da editora é obrigatório.")
    @Size(max=60, message = "A cidade não pode ter mais de 60 caracteres")
    private String cidade;
    @NotBlank(message = "O estado da editora é obrigatório.")
    @Size(max=02, message = "A cidade não pode ter mais de 02 caracteres")
    private String estado;

    @Override
    public String toString() {
        return nome;
    }
}
