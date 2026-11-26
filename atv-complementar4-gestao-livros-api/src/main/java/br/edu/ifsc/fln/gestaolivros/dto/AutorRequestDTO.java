package br.edu.ifsc.fln.gestaolivros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorRequestDTO {
    @NotBlank(message = "O nome do autor é obrigatório.")
    @Size(max = 60, message = "O nome não pode ter mais de 60 caracteres.")
    private String nome;
    @NotBlank(message = "E-mail é obrigatório.")
    @Size(max = 200, message = "O e-mail não pode ter mais de 200 caracteres.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;
    @Size(max = 20, message = "A nacionalidade não pode ter mais de 20 caracteres.")
    private String nacionalidade;
}
