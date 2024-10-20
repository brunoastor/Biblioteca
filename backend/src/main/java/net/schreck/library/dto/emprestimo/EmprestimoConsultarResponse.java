package net.schreck.library.dto.emprestimo;

import lombok.Builder;
import net.schreck.library.domain.emprestimo.Emprestimo;

import java.util.List;

@Builder
public record EmprestimoConsultarResponse (
    String nome,
    String email,
    List<Emprestimo> emprestimos
){
}
