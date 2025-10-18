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
        Mockito.when(armazenamentoEditor.salvar(editor))
                .thenAnswer(invocacao -> {
                    Editor editorPassado = invocacao.getArgument(0, Editor.class);
                    editorPassado.setId(1L);
                    return editorPassado;
                });
        //.thenReturn(new Editor(1L, "Ivan", "ivan@email.com",
        //      BigDecimal.valueOf(30), true));
    }

    @Test
    void dado_editor_valido_quando_criar_entao_deve_retornar_um_id_de_cadastro() {
        Editor resultado = cadastroEditor.criar(editor);
        long esperado = 1L;
        assertEquals(esperado, resultado.getId());
    }
}