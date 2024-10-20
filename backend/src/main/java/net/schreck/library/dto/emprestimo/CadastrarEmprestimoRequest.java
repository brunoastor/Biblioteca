package net.schreck.library.dto.emprestimo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.util.List;

@Builder
public record CadastrarEmprestimoRequest(
        @NotNull Long idCliente,
        @NotNull List<Long> idLivros
) {}
