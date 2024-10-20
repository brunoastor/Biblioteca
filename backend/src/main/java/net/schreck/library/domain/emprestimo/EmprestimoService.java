package net.schreck.library.domain.emprestimo;

import lombok.RequiredArgsConstructor;
import net.schreck.library.domain.emprestimo_livro.EmprestimoLivro;
import net.schreck.library.domain.livro.Livro;
import net.schreck.library.domain.livro.LivroService;
import net.schreck.library.domain.usuario.Usuario;
import net.schreck.library.domain.usuario.UsuarioService;
import net.schreck.library.dto.emprestimo.CadastrarEmprestimoRequest;
import net.schreck.library.dto.emprestimo.EmprestimoConsultarResponse;
import net.schreck.library.dto.emprestimo.EmprestimoLivrosResponse;
import net.schreck.library.dto.emprestimo.EmprestimoCadastrarResponse;
import net.schreck.library.enums.StatusEmprestimo;
import net.schreck.library.enums.StatusLivro;
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

    public EmprestimoCadastrarResponse emprestar(CadastrarEmprestimoRequest request){

        var usuario = usuarioService.consultar(request.idCliente());
        List<Livro> livros = livroService.consultarTodos(request.idLivros());

        var emprestimo = repository.save(criarEmprestimo(usuario, livros));
        livroService.setEmprestados(livros);

        return emprestarResponse(emprestimo);
    }

    public EmprestimoConsultarResponse consultarPorUsuario(Long usuarioId){
        var usuario = usuarioService.consultar(usuarioId);

        var emprestimos = repository.findByUsuario(usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, EMPRESTIMO_NAO_ENCONTRADO));

        return consultarResponse(usuario, emprestimos);
    }

    public void devolver(Long emprestimoId) {
        var emprestimo = consultarPorEmprestimo(emprestimoId);

        if(emprestimo.getLivros().isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, LIVROS_NAO_ENCONTRADOS);
        }

        List<Livro> livros = emprestimo.getLivros().stream()
                .map(EmprestimoLivro::getLivro)
                .peek(livro -> livro.setStatus(StatusLivro.DISPONIVEL))
                .collect(Collectors.toList());

        livroService.atualizarStatus(livros);
        atualizarStatus(emprestimo);
    }

    private void atualizarStatus(Emprestimo emprestimo){
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        repository.save(emprestimo);
    }

    private Emprestimo consultarPorEmprestimo(Long emprestimoId){

        return repository.findById(emprestimoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, EMPRESTIMO_NAO_ENCONTRADO));
    }

    private EmprestimoCadastrarResponse emprestarResponse(Emprestimo emprestimo) {
        List<EmprestimoLivrosResponse> livrosResponse = emprestimo.getLivros().stream()
                .map(livro ->
                        EmprestimoLivrosResponse.builder()
                        .id(livro.getLivro().getId())
                        .nome(livro.getLivro().getTitulo())
                        .build())
                .collect(Collectors.toList());

        return EmprestimoCadastrarResponse.builder()
                .id(emprestimo.getId())
                .nomeUsuario(emprestimo.getUsuario().getNome())
                .livros(livrosResponse)
                .dataEmprestimo(emprestimo.getDataEmprestimo())
                .dataDevolucao(emprestimo.getDataDevolucao())
                .build();
    }

    private EmprestimoConsultarResponse consultarResponse(Usuario usuario, List<Emprestimo> emprestimos){

        return EmprestimoConsultarResponse.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .emprestimos(emprestimos)
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
