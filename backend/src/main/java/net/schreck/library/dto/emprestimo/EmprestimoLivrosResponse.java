package net.schreck.library.dto.emprestimo;

import lombok.Builder;

@Builder
public record EmprestimoLivrosResponse(
        Long id,
        String nome
) {
}
