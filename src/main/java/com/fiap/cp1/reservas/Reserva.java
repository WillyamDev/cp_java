package com.fiap.cp1.reservas;

import com.fiap.cp1.livro.Livro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Reservas")
@Table(name = "reservas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "livro_isbn", unique = true)
    private Livro livro;

    // Construtor simplificado para focar apenas no livro
    public Reserva(Livro livro) {
        this.livro = livro;
    }
}