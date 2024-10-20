package net.schreck.library.domain.emprestimo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.schreck.library.domain.livro.Livro;
import net.schreck.library.domain.usuario.Usuario;
import net.schreck.library.enums.StatusEmprestimo;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @OneToMany
    @JoinColumn(name = "emprestimo_id")
    private List<Livro> livros;

    @NotNull
    private LocalDate dataEmprestimo;

    @NotNull
    private LocalDate dataDevolucao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;
}
