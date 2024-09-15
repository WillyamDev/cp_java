CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL,
    livro_isbn BIGINT,
    CONSTRAINT fk_livro FOREIGN KEY (livro_isbn) REFERENCES livros(isbn)
);