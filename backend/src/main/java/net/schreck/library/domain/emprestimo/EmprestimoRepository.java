package net.schreck.library.domain.emprestimo;

import net.schreck.library.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Optional<List<Emprestimo>> findByUsuario(Usuario usuario);

}
