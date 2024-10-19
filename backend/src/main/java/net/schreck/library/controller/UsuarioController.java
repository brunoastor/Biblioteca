package net.schreck.library.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.schreck.library.domain.usuario.Usuario;
import net.schreck.library.domain.usuario.UsuarioService;
import net.schreck.library.dto.usuario.AlterarUsuarioRequest;
import net.schreck.library.dto.usuario.CadastroUsuarioRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "Usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> cadastro(@RequestBody @Validated CadastroUsuarioRequest request){

        var usuario = service.cadastrar(request);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{clienteId}")
    ResponseEntity<Usuario> obter(@PathVariable("clienteId") Long clienteId) {

        var usuario = service.consultar(clienteId);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/atualizar")
    ResponseEntity<Usuario> atualizar(@RequestBody @Validated AlterarUsuarioRequest request){

        var usuario = service.alterar(request);

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/excluir/{clienteId}")
    ResponseEntity<Void> excluir(@PathVariable("clienteId") Long clienteId){

        service.excluir(clienteId);

        return ResponseEntity.noContent().build();
    }
}
