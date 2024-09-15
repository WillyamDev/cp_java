CREATE TABLE livros (
    isbn BIGINT NOT NULL AUTO_INCREMENT,
    autor VARCHAR(100) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (isbn)  -- Corrigido para usar 'isbn' corretamente
);