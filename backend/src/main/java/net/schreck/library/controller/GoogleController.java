package net.schreck.library.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.schreck.library.infra.integracao.GoogleIntegracao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/google")
@Tag(name = "Emprestimo")
@RequiredArgsConstructor
public class GoogleController {

    //TODO

    private final GoogleIntegracao integracao;

    @GetMapping("/{request}")
    public String consultar(@PathVariable String request){
         return integracao.googleAPIget(request);
    }
}
