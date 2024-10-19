package net.schreck.library.dto.usuario;

import java.time.LocalDate;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        LocalDate dataCadastro,
        String celular
) {
}
