package net.schreck.library.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioRequest(
    @NotNull String nome,
    @NotNull @Email String email,
    @NotNull String celular
){}
