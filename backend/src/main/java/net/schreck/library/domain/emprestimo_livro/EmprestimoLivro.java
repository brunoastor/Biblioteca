package net.schreck.library.domain.emprestimo_livro;

import jakarta.persistence.*;
import lombok.*;
import net.schreck.library.domain.emprestimo.Emprestimo;
import net.schreck.library.domain.livro.Livro;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "emprestimo_livro")
public class EmprestimoLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emprestimo_id", nullable = false)
    private Emprestimo emprestimo;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

}

