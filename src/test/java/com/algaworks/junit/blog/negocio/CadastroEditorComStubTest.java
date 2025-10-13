package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorComStubTest {

    static CadastroEditor cadastroEditor;
    Editor editor;

    @BeforeAll
    static void setUpAll() {
        cadastroEditor = new CadastroEditor(new ArmazenamentoEditorFixoEmMemoria(), new GerenciadorEnvioEmail() {
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

    private static class ArmazenamentoEditorFixoEmMemoria implements ArmazenamentoEditor {
        @Override
        public Editor salvar(Editor editor) {
            return editor;
        }

        @Override
        public Optional<Editor> encontrarPorId(Long editor) {
            return Optional.empty();
        }

        @Override
        public Optional<Editor> encontrarPorEmail(String email) {
            return Optional.empty();
        }

        @Override
        public Optional<Editor> encontrarPorEmailComIdDiferenteDe(String email, Long id) {
            return Optional.empty();
        }

        @Override
        public void remover(Long editorId) {

        }

        @Override
        public List<Editor> encontrarTodos() {
            return List.of();
        }
    }
}