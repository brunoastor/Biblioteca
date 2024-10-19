package net.schreck.library.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record ConsultarUsuarioRequest(
    String nome,
    String email,
    String celular
){}
