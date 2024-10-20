package net.schreck.library.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.schreck.library.domain.emprestimo.EmprestimoService;
import net.schreck.library.dto.emprestimo.CadastrarEmprestimoRequest;
import net.schreck.library.dto.emprestimo.EmprestimoCadastrarResponse;
import net.schreck.library.dto.emprestimo.EmprestimoConsultarResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/emprestimo")
@Tag(name = "Emprestimo")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService service;

    @PostMapping
    public ResponseEntity<EmprestimoCadastrarResponse> cadastrar(@RequestBody @Validated CadastrarEmprestimoRequest request){

        var emprestimo = service.emprestar(request);

        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<EmprestimoConsultarResponse> consultar(@PathVariable("id") Long id){

        var emprestimo = service.consultarPorUsuario(id);

        return ResponseEntity.ok(emprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> devolver(@PathVariable("id") Long id){

        service.devolver(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public void alterar(){
        //TODO
    }

    @DeleteMapping
    public void excluir(){
        //TODO
    }

}
