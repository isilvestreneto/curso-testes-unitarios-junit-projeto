package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
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

    @Nested
    class CenariosEditorValido {
        @BeforeEach
        void setUp() {
            editor = new Editor(1L, "Ivan", "ivan@email.com", BigDecimal.valueOf(30), true);
        }

        @Nested
        class CadastroEditorValido {
            @BeforeEach
            void setUp() {
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

        @Nested
        class EditarEditorValido {

            @BeforeEach
            void setUp() {

            }

            @Test
            void Dado_um_editor_valido_Quando_editar_Deve_acionar_armazenamento_editor_e_retornar_editor_atualizado() {
                when(armazenamentoEditor.encontrarPorId(editor.getId())).thenReturn(Optional.of(editor));
                cadastroEditor.editar(editor);
                verify(armazenamentoEditor, times(1)).salvar(any(Editor.class));
            }

            @Test
            void Quando_editar_editor_usar_email_ja_cadastro_em_outro_id_Deve_lancar_exception() {
                when(armazenamentoEditor.encontrarPorEmailComIdDiferenteDe(anyString(), anyLong()))
                        .thenReturn(Optional.of(new Editor(2L, "Outro", "ivan@email.com", BigDecimal.ONE, true)));
                assertThrows(RegraNegocioException.class, () -> cadastroEditor.editar(editor));
            }

        }

        @Nested
        class RemoverEditorValido {
            @BeforeEach
            void setUp() {
            }

            @Test
            void Quando_id_de_editor_for_nulo_Deve_lancar_Nullponter_Exception() throws Exception {
                assertThrows(NullPointerException.class, () -> cadastroEditor.remover(null));
            }

            @Test
            void Quando_id_de_editor_for_valido_E_existir_editor_na_base_Deve_remover_com_sucesso() throws Exception {
                when(armazenamentoEditor.encontrarPorId(editor.getId())).thenReturn(Optional.of(editor));
                cadastroEditor.remover(editor.getId());
                verify(armazenamentoEditor, times(1)).remover(any(Long.class));
            }
        }
    }


    @Nested
    class CenariosEditorInvalido {
        @Nested
        class CadastroEditorInvalido {
            @Test
            void Dado_um_editor_nulo_Quando_cadastrar_Entao_deve_lancar_exception() {
                assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
                verify(gerenciadorEnvioEmail, never()).enviarEmail(any());
            }
        }

        @Nested
        class EditarEditorInvalido {
            @Test
            void Dado_um_editor_invalido_Quando_editar_Deve_lancar_exception_e_nao_acionar_armazenamento_editor() {
                assertThrows(NullPointerException.class, () -> cadastroEditor.editar(null));
                verify(armazenamentoEditor, never()).salvar(any());
            }
        }
    }

}