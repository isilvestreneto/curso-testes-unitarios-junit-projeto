package com.algaworks.junit.blog.utilidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConversorSlugTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void Deve_converter_junto_com_codigo() {
        try (MockedStatic<GeradorCodigo> mockedStatic = mockStatic(GeradorCodigo.class)) {
            mockedStatic.when(GeradorCodigo::gerar).thenReturn("12345");
            String slug = ConversorSlug.converterJuntoComCodigo("Oi");
            assertEquals("oi-12345", slug);
        }
    }
}