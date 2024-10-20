package net.schreck.library.domain.usuario;

import lombok.RequiredArgsConstructor;
import net.schreck.library.dto.usuario.AlterarUsuarioRequest;
import net.schreck.library.dto.usuario.CadastroUsuarioRequest;
import net.schreck.library.dto.usuario.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static net.schreck.library.utils.MensagemPadrao.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario cadastrar(CadastroUsuarioRequest request){

        var usuario = repository.findByEmail(request.email());

        if(usuario.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, USUARIO_DUPLICADO);

        return repository.save(
                Usuario.builder()
                        .nome(request.nome())
                        .email(request.email())
                        .celular(request.celular())
                        .dataCadastro(LocalDate.now())
                        .build());
    }

    public Usuario consultar(Long usuarioId){
        return repository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));
    }


    public Usuario alterar(AlterarUsuarioRequest request){

        var usuario = repository.findById(request.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));

        if (request.nome() != null && !request.nome().isEmpty()) {
            usuario.setNome(request.nome());
        }
        if (request.email() != null && !request.email().isEmpty()) {
            usuario.setEmail(request.email());
        }
        if (request.celular() != null && !request.celular().isEmpty()) {
            usuario.setCelular(request.celular());
        }

        return repository.save(usuario);
    }

    public void excluir(Long usuarioId){
        var usuario = repository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));

        repository.delete(usuario);
    }
}
