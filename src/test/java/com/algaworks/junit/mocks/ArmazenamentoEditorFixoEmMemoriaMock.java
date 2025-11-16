package com.algaworks.junit.mocks;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;

import java.util.List;
import java.util.Optional;

public class ArmazenamentoEditorFixoEmMemoriaMock implements ArmazenamentoEditor {
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
        return Optional.of(new Editor(

        ));
    }

    @Override
    public void remover(Long editorId) {
        System.out.println("Editor " + editorId + " removido com sucesso!");
    }

    @Override
    public List<Editor> encontrarTodos() {
        return List.of();
    }
}
