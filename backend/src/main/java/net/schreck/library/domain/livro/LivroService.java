package net.schreck.library.domain.livro;

import lombok.RequiredArgsConstructor;
import net.schreck.library.dto.livro.AlterarLivroRequest;
import net.schreck.library.dto.livro.CadastroLivroRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                        .build());
    }

    public Livro consultar(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, LIVRO_NAO_ENCONTRADO));
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

        repository.delete(livro);
    }
}
