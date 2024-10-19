package net.schreck.library.domain.usuario;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static net.schreck.library.utils.MensagemPadrao.CLIENTE_DUPLICADO;

import net.schreck.library.dto.usuario.CadastroUsuarioRequest;
import net.schreck.library.dto.usuario.UsuarioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.cadastro(request);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(CLIENTE_DUPLICADO, exception.getReason());

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

        var usuarioCadastrado = service.cadastro(request);

        assertNotNull(usuarioCadastrado);
        assertEquals("Usuario", usuarioCadastrado.getNome());
        assertEquals("usuario@email.com", usuarioCadastrado.getEmail());
        assertEquals("999999999", usuarioCadastrado.getCelular());
        assertEquals(LocalDate.now(), usuarioCadastrado.getDataCadastro());

        verify(repository, times(1)).save(any(Usuario.class));
    }
}