package br.edu.ifsc.fln.gestaolivros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditoraResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String cidade;
    private String estado;
}
