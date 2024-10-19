package net.schreck.library.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.schreck.library.domain.usuario.Usuario;
import net.schreck.library.domain.usuario.UsuarioService;
import net.schreck.library.dto.usuario.CadastroUsuarioRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static java.util.Objects.isNull;
import static net.schreck.library.utils.MensagemPadrao.*;

@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "Usuario API", description = "CRUD Usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> cadastro(@RequestBody @Validated CadastroUsuarioRequest request){

        var usuario = service.cadastro(request);

        return ResponseEntity.ok(usuario);
    }


}
