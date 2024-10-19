package net.schreck.library.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioRequest(
    @NotNull String nome,
    @NotNull String email,
    @NotNull String celular
){}
