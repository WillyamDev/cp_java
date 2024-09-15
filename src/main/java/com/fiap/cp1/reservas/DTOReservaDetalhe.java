package com.fiap.cp1.reservas;

public record DTOReservaDetalhe(Long id, Long livroIsbn, String livroTitulo) {

    public DTOReservaDetalhe(Reserva reserva) {
        this(reserva.getId(), reserva.getLivro().getIsbn(), reserva.getLivro().getTitulo());
    }
}