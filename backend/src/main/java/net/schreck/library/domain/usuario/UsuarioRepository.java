package net.schreck.library.domain.usuario;

import lombok.NonNull;
import net.schreck.library.dto.usuario.UsuarioResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Transactional(readOnly = true)
    Optional<UsuarioResponse> findByEmail(String email);

}
