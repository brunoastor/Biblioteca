package net.schreck.library.domain.emprestimo;

import lombok.RequiredArgsConstructor;
import net.schreck.library.domain.emprestimo_livro.EmprestimoLivro;
import net.schreck.library.domain.livro.Livro;
import net.schreck.library.domain.livro.LivroService;
import net.schreck.library.domain.usuario.Usuario;
import net.schreck.library.domain.usuario.UsuarioService;
import net.schreck.library.dto.emprestimo.CadastrarEmprestimoRequest;
import net.schreck.library.dto.emprestimo.EmprestimoLivrosResponse;
import net.schreck.library.dto.emprestimo.EmprestimoResponse;
import net.schreck.library.enums.StatusEmprestimo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static net.schreck.library.utils.MensagemPadrao.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final UsuarioService usuarioService;
    private final LivroService livroService;

    private final EmprestimoRepository repository;

    public EmprestimoResponse emprestar(CadastrarEmprestimoRequest request){

        var usuario = usuarioService.consultar(request.idCliente());
        List<Livro> livros = livroService.consultarTodos(request.idLivros());

        var emprestimo = repository.save(criarEmprestimo(usuario, livros));
        livroService.setEmprestados(livros);

        return emprestarToResponse(emprestimo);
    }

    public EmprestimoResponse consultar(Long usuarioId){
        var usuario = usuarioService.consultar(usuarioId);

        var emprestimo = repository.findByUsuario(usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, EMPRESTIMO_NAO_ENCONTRADO));

        return emprestarToResponse(emprestimo);
    }

    private EmprestimoResponse emprestarToResponse(Emprestimo emprestimo) {
        List<EmprestimoLivrosResponse> livrosResponse = emprestimo.getLivros().stream()
                .map(livro ->
                        EmprestimoLivrosResponse.builder()
                        .id(livro.getLivro().getId())
                        .nome(livro.getLivro().getTitulo())
                        .build())
                .collect(Collectors.toList());

        return EmprestimoResponse.builder()
                .id(emprestimo.getId())
                .nomeUsuario(emprestimo.getUsuario().getNome())
                .livros(livrosResponse)
                .dataEmprestimo(emprestimo.getDataEmprestimo())
                .dataDevolucao(emprestimo.getDataDevolucao())
                .build();
    }

    private EmprestimoLivro criarEmprestimoLivro(Livro livro, Emprestimo emprestimo) {
        return EmprestimoLivro.builder()
                .livro(livro)
                .emprestimo(emprestimo)
                .build();
    }

    private Emprestimo criarEmprestimo(Usuario usuario, List<Livro> livros) {
        Emprestimo emprestimo = Emprestimo.builder()
                .usuario(usuario)
                .dataEmprestimo(LocalDate.now())
                .dataDevolucao(LocalDate.now().plusDays(7))
                .status(StatusEmprestimo.EMPRESTADO)
                .build();

        List<EmprestimoLivro> emprestimoLivros = livros.stream()
                .map(livro -> criarEmprestimoLivro(livro, emprestimo))
                .collect(Collectors.toList());

        emprestimo.setLivros(emprestimoLivros);

        return emprestimo;
    }


}
