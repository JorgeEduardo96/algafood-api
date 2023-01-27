package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSemSenhaInput {

    @NotBlank
    @Email
    @Schema(example = "jorge_primo@gmail.com")
    private String email;

    @NotBlank
    @Schema(example = "Jorge Primo")
    private String nome;

}
