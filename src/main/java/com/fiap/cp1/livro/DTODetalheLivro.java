package com.fiap.cp1.livro;

public record DTODetalheLivro(Long isbn,String autor, String titulo, Categoria categoria) {
    public DTODetalheLivro(Livro livro) {
        this( livro.getIsbn(), livro.getAutor(), livro.getTitulo(), livro.getCategoria());

    }
}
