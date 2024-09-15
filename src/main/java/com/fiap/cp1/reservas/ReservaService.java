package com.fiap.cp1.reservas;

import com.fiap.cp1.livro.Livro;
import com.fiap.cp1.livro.LivroRepository;
import com.fiap.cp1.Exceptions.TratadorDeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional
    public DTOReservaDetalhe reservarLivro(Long isbn) {
        Livro livro = livroRepository.findById(isbn)
                .orElseThrow(() -> new TratadorDeException.TratarErro404("Livro não encontrado com ISBN: " + isbn));

        if (!livro.isAtivo()) {
            throw new TratadorDeException.TratarErro400("Livro não está ativo.");
        }

        if (isLivroReservado(livro)) {
            throw new TratadorDeException.TratarErro400("O livro já está reservado.");
        }

        Reserva reserva = new Reserva(livro);
        reservaRepository.save(reserva);
        return new DTOReservaDetalhe(reserva);
    }

    private boolean isLivroReservado(Livro livro) {
        return reservaRepository.findByLivro(livro).isPresent();
    }

    public Page<DTOReservaLista> listarReservas(Pageable paginacao) {
        return reservaRepository.findAll(paginacao).map(DTOReservaLista::new);
    }

    public DTOReservaDetalhe buscarReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new TratadorDeException.TratarErro404("Reserva não encontrada com ID: " + id));
        return new DTOReservaDetalhe(reserva);
    }

    @Transactional
    public void cancelarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new TratadorDeException.TratarErro404("Reserva não encontrada com ID: " + reservaId));
        reservaRepository.delete(reserva);
    }
}