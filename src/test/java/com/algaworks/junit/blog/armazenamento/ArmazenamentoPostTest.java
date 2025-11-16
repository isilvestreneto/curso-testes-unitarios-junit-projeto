package com.algaworks.junit.blog.armazenamento;

import com.algaworks.junit.blog.modelo.Post;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArmazenamentoPostMock implements ArmazenamentoPost {

    @Override
    public Post salvar(Post post) {
        return post;
    }

    @Override
    public Optional<Post> encontrarPorId(Long post) {
        return Optional.empty();
    }

    @Override
    public void remover(Long postId) {

    }

    @Override
    public List<Post> encontrarTodos() {
        return List.of();
    }
}