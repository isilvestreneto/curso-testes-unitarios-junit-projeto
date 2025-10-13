package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorComMockTest {

    CadastroEditor cadastroEditor;
    Editor editor;

    @BeforeEach
    void setUp() {
        editor = new Editor(null, "Ivan", "ivan@email.com", BigDecimal.valueOf(30), true);

        ArmazenamentoEditor armazenamentoEditor = Mockito.mock(ArmazenamentoEditor.class);
        Mockito.when(armazenamentoEditor.salvar(editor))
                .thenReturn(new Editor(1L, "Ivan", "ivan@email.com",
                        BigDecimal.valueOf(30), true));
        GerenciadorEnvioEmail gerenciadorEnvioEmail = Mockito.mock(GerenciadorEnvioEmail.class);
        cadastroEditor = new CadastroEditor(armazenamentoEditor, gerenciadorEnvioEmail);
    }

    @Test
    void dado_editor_valido_quando_criar_entao_deve_retornar_um_id_de_cadastro() {
        Editor resultado = cadastroEditor.criar(editor);
        long esperado = 1L;
        assertEquals(esperado, resultado.getId());
    }
}