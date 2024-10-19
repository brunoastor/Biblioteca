package net.schreck.library.domain.livro;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String autor;

    @NotNull
    private String isbn;

    @NotNull
    @Column(name = "data_publicacao")
    private String dataPublicacao;

    @NotNull
    private String categoria;

}
