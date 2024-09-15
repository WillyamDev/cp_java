package com.fiap.cp1.livro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "livros")
@Entity(name = "Livros")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "isbn")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;
    private String autor;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;



    private boolean ativo;

    public Livro(DTOCadastroLivro dados) {
        this.autor = dados.autor();
        this.titulo = dados.titulo();
        this.ativo = true;
        this.categoria = dados.categoria();

    }



    public void Excluir() {
        this.ativo = false;
    }
}



