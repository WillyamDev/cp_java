package com.fiap.cp1.livro;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTOCadastroLivro(@NotBlank String autor, @NotBlank String titulo, @NotNull Categoria categoria) {



}

