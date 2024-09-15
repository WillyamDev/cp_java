package com.fiap.cp1.reservas;

import com.fiap.cp1.livro.Livro;
import com.fiap.cp1.reservas.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findByLivro(Livro livro);
    //Page<Reserva> findAllByAtivoTrue(Pageable paginacao);

}