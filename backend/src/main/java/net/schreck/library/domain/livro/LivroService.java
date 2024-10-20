package net.schreck.library.domain.livro;

import lombok.RequiredArgsConstructor;
import net.schreck.library.dto.livro.AlterarLivroRequest;
import net.schreck.library.dto.livro.CadastroLivroRequest;
import net.schreck.library.enums.StatusLivro;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static net.schreck.library.utils.MensagemPadrao.*;


@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro cadastrar(CadastroLivroRequest request){

        return repository.save(
                Livro.builder()
                        .titulo(request.titulo())
                        .autor(request.autor())
                        .isbn(request.isbn())
                        .dataPublicacao(String.valueOf(request.dataPublicacao()))
                        .categoria(request.categoria())
                        .status(StatusLivro.DISPONIVEL)
                        .build());
    }

    public Livro consultar(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, LIVRO_NAO_ENCONTRADO));
    }

    public List<Livro> consultarTodos(List<Long> ids){

        List<Livro> livros = repository.findAllById(ids);

        if (livros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, LIVRO_NAO_ENCONTRADO);
        }
        if (livros.size() != ids.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, LIVROS_NAO_ENCONTRADOS);
        }

        livros.stream()
                .filter(livro -> livro.getStatus().equals(StatusLivro.INDISPONIVEL))
                .findAny()
                .ifPresent(livro -> {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O livro '" + livro.getTitulo() + "' não está disponível!");
                });

        return livros;
    }

    public Livro alterar(AlterarLivroRequest request){

        var livro = repository.findById(request.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, LIVRO_NAO_ENCONTRADO));

        if (request.titulo() != null && !request.titulo().isEmpty()) {
            livro.setTitulo(request.titulo());
        }
        if (request.autor() != null && !request.autor().isEmpty()) {
            livro.setAutor(request.autor());
        }
        if (request.isbn() != null && !request.isbn().isEmpty()) {
            livro.setIsbn(request.isbn());
        }
        if (request.dataPublicacao() != null && !String.valueOf(request.dataPublicacao()).isEmpty()) {
            livro.setDataPublicacao(String.valueOf(request.dataPublicacao()));
        }
        if (request.categoria() != null && !request.categoria().isEmpty()) {
            livro.setCategoria(request.categoria());
        }

        return repository.save(livro);
    }

    public void excluir(Long id){

        var livro = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, LIVRO_NAO_ENCONTRADO));

        if(livro.getStatus().equals(StatusLivro.INDISPONIVEL)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, LIVRO_NAO_DISPONIVEL);
        }

        repository.delete(livro);
    }

    public void setEmprestados(List<Livro> livros){
        livros.forEach(livro -> livro.setStatus(StatusLivro.INDISPONIVEL));
        repository.saveAll(livros);
    }
}
