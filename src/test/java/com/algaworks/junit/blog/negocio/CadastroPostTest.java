package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroPostTest {

    @Mock
    ArmazenamentoPost armazenamentoPost;

    @Mock
    CalculadoraGanhos calculadoraGanhos;

    @Mock
    GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    CadastroPost cadastroPost;

    Post post;
    Editor editor;

    @BeforeEach
    void setUp() {
        cadastroPost = new CadastroPost(armazenamentoPost, calculadoraGanhos, gerenciadorNotificacao);
        editor = new Editor("Editor de Teste", "editor@email.com", BigDecimal.TEN, true);
        post = new Post("Título de Teste", "Conteúdo de Teste", editor, false, false);
    }

    @Nested
    class Quando_argumento_for_nulo_Deve_lancar_Nullpointer_Exception {
        @Test
        void ao_criar() {
            assertThrows(NullPointerException.class, () -> {
                cadastroPost.criar(null);
            });
        }

        @Test
        void ao_editar() {
            assertThrows(NullPointerException.class, () -> {
                cadastroPost.editar(null);
            });
        }

        @Test
        void ao_remover() {
            assertThrows(NullPointerException.class, () -> {
                cadastroPost.remover(null);
            });
        }
    }

    @Nested
    class Cenarios_Criar {
        @Test
        void Criar_post_Deve_acionar_Armazenamento_Post_Salvar_E_Enviar_Notificacao() {
            when(armazenamentoPost.salvar(post)).thenReturn(post);
            cadastroPost.criar(post);
        }
    }

    @Nested
    class Cenarios_Editar {
        @Captor
        ArgumentCaptor<Post> captor;

        @Test
        void Editar_post_Deve_acionar_Armazenamento_Post_Salvar_Com_Post_Nao_Pago() {
            when(armazenamentoPost.encontrarPorId(post.getId()))
                    .thenReturn(Optional.of(post));
            Ganhos ganhos = new Ganhos(BigDecimal.TEN, 10, BigDecimal.valueOf(123.45));
            when(calculadoraGanhos.calcular(post))
                    .thenReturn(ganhos);
            when(armazenamentoPost.salvar(post)).thenReturn(post);

            Post resultado = cadastroPost.editar(post);

            // 1) calculadora foi chamada com o post
            verify(calculadoraGanhos).calcular(post);

            // 2) o post salvo recebeu o valor calculado
            assertEquals(ganhos, post.getGanhos());
            assertSame(post, resultado);
        }

        @Test
        void Editar_post_Deve_acionar_Armazenamento_Post_Salvar_Com_Post_Pago() {
            post.setPago(true);
            when(armazenamentoPost.encontrarPorId(post.getId())).thenReturn(java.util.Optional.of(post));
            when(armazenamentoPost.salvar(post)).thenReturn(post);
            cadastroPost.editar(post);
        }
    }

    @Nested
    class Cenarios_Remover {
        @Test
        void Quando_nao_encontrar_post_Deve_lancar_PostNaoEncontradoException() {
            Long postId = 1L;
            when(armazenamentoPost.encontrarPorId(postId)).thenReturn(Optional.empty());
            assertThrows(com.algaworks.junit.blog.exception.PostNaoEncontradoException.class, () -> {
                cadastroPost.remover(postId);
            });
        }

        @Test
        void Quando_remover_post_ja_publicado_Deve_lancar_RegraNegocioException() {
            Long postId = 1L;
            post.setPublicado(true);
            when(armazenamentoPost.encontrarPorId(postId)).thenReturn(Optional.of(post));
            assertThrows(RegraNegocioException.class, () -> {
                cadastroPost.remover(postId);
            });
        }

        @Test
        void Quando_remover_post_ja_pago_Deve_lancar_RegraNegocioException() {
            Long postId = 1L;
            post.setPago(true);
            when(armazenamentoPost.encontrarPorId(postId)).thenReturn(Optional.of(post));
            assertThrows(RegraNegocioException.class, () -> {
                cadastroPost.remover(postId);
            });
        }

        @Test
        void Quando_remover_post_Deve_acionar_armazenamentoPost_remover_passando_postId_como_parametro() {
            Long postId = 1L;
            when(armazenamentoPost.encontrarPorId(postId)).thenReturn(Optional.of(post));
            cadastroPost.remover(postId);
            verify(armazenamentoPost).remover(postId);
        }
    }
}