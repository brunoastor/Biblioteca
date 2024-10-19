package net.schreck.library.dto.livro;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AlterarLivroRequest(
        @NotNull Long id,
        String titulo,
        String autor,
        String isbn,
        LocalDate dataPublicacao,
        String categoria
) {
}
