package br.edu.ifsc.fln.gestaolivros.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String nacionalidade;
}
