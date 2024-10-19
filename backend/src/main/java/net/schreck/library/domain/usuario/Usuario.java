package net.schreck.library.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Email(message = "Email deve ser válido")
    private String email;

    @NotNull
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @NotNull
    private String celular;
}
