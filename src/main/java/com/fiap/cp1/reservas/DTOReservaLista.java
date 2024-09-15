package com.fiap.cp1.reservas;



public record DTOReservaLista(Long id, Long livroIsbn) {
    // Construtor que mapeia os atributos a partir da entidade Reserva
    public DTOReservaLista(Reserva reserva) {
        this(reserva.getId(), reserva.getLivro().getIsbn());
    }
}