package net.schreck.library.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.schreck.library.domain.livro.Livro;
import net.schreck.library.domain.livro.LivroService;
import net.schreck.library.dto.livro.AlterarLivroRequest;
import net.schreck.library.dto.livro.CadastroLivroRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/livro")
@Tag(name = "Livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<Livro> cadastro(@RequestBody @Validated CadastroLivroRequest request){

        var livro = service.cadastrar(request);

        return ResponseEntity.ok(livro);
    }

    @GetMapping("/{id}")
    ResponseEntity<Livro> obter(@PathVariable("id") Long id) {

        var livro = service.consultar(id);

        return ResponseEntity.ok(livro);
    }

    @PutMapping("/atualizar")
    ResponseEntity<Livro> atualizar(@RequestBody @Validated AlterarLivroRequest request){

        var livro = service.alterar(request);

        return ResponseEntity.ok(livro);
    }

    @DeleteMapping("/excluir/{id}")
    ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
