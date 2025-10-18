package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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
    void dado_editor_valido_quando_criar_entao_deve_retornar_um_id_de_cadastro() {
        Editor resultado = cadastroEditor.criar(editor);
        long esperado = 1L;
        assertEquals(esperado, resultado.getId());
    }

    @Test
    void dado_um_editor_valido_quando_criar_entao_deve_chamar_metodo_salvar_do_armazenamento() {
        cadastroEditor.criar(editor);
        verify(armazenamentoEditor, Mockito.times(1)).salvar(eq(editor));
    }

    @Test
    void dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_deve_enviar_email() {
        when(armazenamentoEditor.salvar(editor)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor));
        verify(gerenciadorEnvioEmail, never()).enviarEmail(any());
    }
}