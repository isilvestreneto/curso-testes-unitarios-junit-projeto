package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.mocks.ArmazenamentoEditorFixoEmMemoriaMock;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorComStubTest {

    static CadastroEditor cadastroEditor;
    Editor editor;

    @BeforeAll
    static void setUpAll() {
        cadastroEditor = new CadastroEditor(new ArmazenamentoEditorFixoEmMemoriaMock(), new GerenciadorEnvioEmail() {
            @Override
            void enviarEmail(Mensagem mensagem) {
                System.out.println("Enviando mensagem:" + mensagem.toString());
            }
        });
    }

    @BeforeEach
    void beforeEach() {
        editor = new Editor("Ivan", "isilvestreneto@gmail.com", BigDecimal.TEN, false);
    }

    @Test
    void criar_deve_retornar_entidade_criada_dado_editor_valido() {
        Editor result = cadastroEditor.criar(editor);
        assertEquals(result, editor);
    }

    @Test
    void criar_deve_retornar_exception_dado_editor_invalido() {
        editor = null;
        assertThrows(RuntimeException.class, () -> {
            cadastroEditor.criar(editor);
        });
    }

}