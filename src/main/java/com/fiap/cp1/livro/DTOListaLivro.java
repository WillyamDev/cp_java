package com.fiap.cp1.livro;

public record DTOListaLivro(Long isbn,String autor, String titulo, Categoria categoria) {

    public DTOListaLivro(Livro livro) {
        this(livro.getIsbn(), livro.getAutor(), livro.getTitulo(), livro.getCategoria());

    }
}
