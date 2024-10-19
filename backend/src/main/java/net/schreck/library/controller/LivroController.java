package net.schreck.library.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.schreck.library.domain.livro.Livro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/livro")
@Tag(name = "Livro API")
@RequiredArgsConstructor
public class LivroController {


}
