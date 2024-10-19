package net.schreck.library.domain.usuario;

import static net.schreck.library.utils.MensagemPadrao.USUARIO_NAO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static net.schreck.library.utils.MensagemPadrao.USUARIO_DUPLICADO;

import net.schreck.library.dto.usuario.AlterarUsuarioRequest;
import net.schreck.library.dto.usuario.CadastroUsuarioRequest;
import net.schreck.library.dto.usuario.UsuarioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Test
    public void bloqueiaUsuarioDuplicado(){

        CadastroUsuarioRequest request = new CadastroUsuarioRequest("Usuario", "usuario@email.com", "999999999");
        UsuarioResponse usuarioResponse = new UsuarioResponse(1L, "Usuario", "usuario@email.com", LocalDate.now(), "999999999");

        when(repository.findByEmail(request.email())).thenReturn(Optional.of(usuarioResponse));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.cadastrar(request));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(USUARIO_DUPLICADO, exception.getReason());

        verify(repository, never()).save(any());
    }

    @Test
    public void cadastrarUsuario(){

        CadastroUsuarioRequest request = new CadastroUsuarioRequest("Usuario", "usuario@email.com", "999999999");

        when(repository.findByEmail(request.email())).thenReturn(Optional.empty());

        Usuario usuarioEsperado = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .celular(request.celular())
                .dataCadastro(LocalDate.now())
                .build();

        when(repository.save(any(Usuario.class))).thenReturn(usuarioEsperado);

        var usuarioCadastrado = service.cadastrar(request);

        assertNotNull(usuarioCadastrado);
        assertEquals("Usuario", usuarioCadastrado.getNome());
        assertEquals("usuario@email.com", usuarioCadastrado.getEmail());
        assertEquals("999999999", usuarioCadastrado.getCelular());
        assertEquals(LocalDate.now(), usuarioCadastrado.getDataCadastro());

        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void alterarUsuarioSucesso() {

        Usuario usuario = new Usuario(1L, "Nome Antigo", "email@example.com", LocalDate.now(),"999999999");
        AlterarUsuarioRequest request = new AlterarUsuarioRequest(1L, "Nome Novo", "novoemail@email.com", "888888888");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioAtualizado = service.alterar(request);

        assertEquals("Nome Novo", usuarioAtualizado.getNome());
        assertEquals("novoemail@email.com", usuarioAtualizado.getEmail());
        assertEquals("888888888", usuarioAtualizado.getCelular());
        verify(repository).save(usuario);
    }

    @Test
    void alterarUsuarioNaoEncontrado() {

        AlterarUsuarioRequest request = new AlterarUsuarioRequest(2L, "Novo Usuario", "novoemail@example.com", "987654321");
        when(repository.findById(2L)).thenReturn(Optional.empty());

        var exception = assertThrows(ResponseStatusException.class, () -> service.alterar(request));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(USUARIO_NAO_ENCONTRADO, exception.getReason());
    }

    @Test
    public void excluirUsuario() {
        Long id = 1L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);
        usuarioExistente.setNome("Usuario");
        usuarioExistente.setEmail("usuario@email.com");
        usuarioExistente.setCelular("999999999");

        when(repository.findById(id)).thenReturn(Optional.of(usuarioExistente));

        service.excluir(id);

        verify(repository, times(1)).delete(usuarioExistente);
    }

    @Test
    public void excluirUsuarioNaoExistente() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.excluir(id));

        verify(repository, never()).delete(any(Usuario.class));
    }
}