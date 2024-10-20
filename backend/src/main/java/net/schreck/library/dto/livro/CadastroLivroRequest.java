package net.schreck.library.dto.livro;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastroLivroRequest(
        @NotNull String titulo,
        @NotNull String autor,
        @NotNull String isbn,
        @NotNull LocalDate dataPublicacao,
        @NotNull String categoria
) {
}
