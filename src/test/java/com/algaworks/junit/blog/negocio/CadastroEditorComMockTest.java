package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroEditorComMockTest {

    Editor editor;

    @Mock
    ArmazenamentoEditor armazenamentoEditor;

    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;

    @Captor
    ArgumentCaptor<Mensagem> captor;

    @BeforeEach
    void setUp() {
        editor = new Editor(null, "Ivan", "ivan@email.com", BigDecimal.valueOf(30), true);
        when(armazenamentoEditor.salvar(any(Editor.class)))
                .thenAnswer(invocacao -> {
                    Editor editorPassado = invocacao.getArgument(0, Editor.class);
                    editorPassado.setId(1L);
                    return editorPassado;
                });
    }

    @Test
    void Dado_editor_valido_Quando_criar_entao_Deve_retornar_um_id_de_cadastro() {
        Editor resultado = cadastroEditor.criar(editor);
        long esperado = 1L;
        assertEquals(esperado, resultado.getId());
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_entao_Deve_chamar_metodo_salvar_do_armazenamento() {
        cadastroEditor.criar(editor);
        verify(armazenamentoEditor, Mockito.times(1)).salvar(eq(editor));
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_Deve_enviar_email() {
        when(armazenamentoEditor.salvar(editor)).thenThrow(new RuntimeException());

        assertAll("Nao Deve enviar email, Quando lancar exception no armazenamento",
                () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                () -> verify(gerenciadorEnvioEmail, never()).enviarEmail(any())
        );
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_Deve_enviar_email_com_destino_ao_editor() {
        Editor editorSalvo = cadastroEditor.criar(editor);
        verify(gerenciadorEnvioEmail).enviarEmail(captor.capture());
        Mensagem mensagem = captor.getValue();

        assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_Deve_verificar_email() {
        Editor editorSpy = spy(editor);
        cadastroEditor.criar(editorSpy);
        verify(editorSpy, atLeast(1)).getEmail();
    }

    @Test
    void Dado_um_editor_com_email_existente_Deve_lancar_exception() {
        when(armazenamentoEditor.encontrarPorEmail("ivan@email.com"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(editor));
        Editor editorComMesmoEmail = new Editor(null, "Ivan", "ivan@email.com", BigDecimal.valueOf(30), true);
        cadastroEditor.criar(editor);
        assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComMesmoEmail));
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Deve_enviar_email_ao_salvar() {
        cadastroEditor.criar(editor);
        InOrder inOrder = inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
        inOrder.verify(armazenamentoEditor, times(1)).salvar(eq(editor));
        inOrder.verify(gerenciadorEnvioEmail, times(1))
                .enviarEmail(any(Mensagem.class));

    }
}