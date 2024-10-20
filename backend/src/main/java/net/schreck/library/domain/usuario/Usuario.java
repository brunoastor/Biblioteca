package net.schreck.library.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.schreck.library.domain.emprestimo.Emprestimo;

import java.time.LocalDate;
import java.util.List;

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
