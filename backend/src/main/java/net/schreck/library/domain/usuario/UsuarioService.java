package net.schreck.library.domain.usuario;

import lombok.RequiredArgsConstructor;
import net.schreck.library.dto.usuario.CadastroUsuarioRequest;
import net.schreck.library.dto.usuario.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static net.schreck.library.utils.MensagemPadrao.CLIENTE_DUPLICADO;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario cadastro(CadastroUsuarioRequest request){

        Optional<UsuarioResponse> usuario = repository.findByEmail(request.email());

        if(usuario.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, CLIENTE_DUPLICADO);

        return repository.save(
                Usuario.builder()
                        .nome(request.nome())
                        .email(request.email())
                        .celular(request.celular())
                        .dataCadastro(LocalDate.now())
                        .build());
    }

    public Usuario consultar(CadastroUsuarioRequest request){


        UsuarioResponse usuario = repository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, CLIENTE_DUPLICADO));

        return null;
    }
}
