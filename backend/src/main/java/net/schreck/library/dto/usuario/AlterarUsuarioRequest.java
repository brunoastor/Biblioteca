package net.schreck.library.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AlterarUsuarioRequest (
        @NotNull Long id,
        String nome,
        @Email String email,
        String celular
){}
