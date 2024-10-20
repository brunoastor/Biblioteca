package net.schreck.library.dto.emprestimo;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EmprestimoResponse(
        Long id,
        String nomeUsuario,
        List<EmprestimoLivrosResponse> livros,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao
) {
}
