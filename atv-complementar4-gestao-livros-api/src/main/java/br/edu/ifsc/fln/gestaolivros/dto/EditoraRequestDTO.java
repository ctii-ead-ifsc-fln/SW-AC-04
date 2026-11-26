package br.edu.ifsc.fln.gestaolivros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditoraRequestDTO {
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
}
