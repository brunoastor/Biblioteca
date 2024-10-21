package net.schreck.library.domain.emprestimo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.schreck.library.domain.emprestimo_livro.EmprestimoLivro;
import net.schreck.library.domain.usuario.Usuario;
import net.schreck.library.enums.StatusEmprestimo;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL)
    private List<EmprestimoLivro> livros = new ArrayList<>();

    @NotNull
    @JoinColumn(name = "data_emprestimo")
    private LocalDate dataEmprestimo;

    @NotNull
    @JoinColumn(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;
}
