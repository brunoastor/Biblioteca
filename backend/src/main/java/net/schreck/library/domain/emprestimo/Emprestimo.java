package net.schreck.library.domain.emprestimo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.schreck.library.enums.Status;

import java.time.LocalDate;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String usuarioId;

    @NotNull
    private String livroId;

    @NotNull
    private String dataEmprestimo;

    @NotNull
    private LocalDate dataDevolucao;

    @NotNull
    private Status status;
}
