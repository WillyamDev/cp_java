package com.fiap.cp1.controller;

import com.fiap.cp1.reservas.ReservaService;
import com.fiap.cp1.reservas.DTOCadastroReserva;
import com.fiap.cp1.reservas.DTOReservaDetalhe;
import com.fiap.cp1.reservas.DTOReservaLista;
import com.fiap.cp1.Exceptions.TratadorDeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    // Endpoint para criar uma nova reserva
    @PostMapping
    @Transactional
    public ResponseEntity<DTOReservaDetalhe> reservarLivro(@RequestBody @Valid DTOCadastroReserva dto, UriComponentsBuilder uriComponentsBuilder) {
        var reserva = service.reservarLivro(dto.livroIsbn());
        if (reserva == null) {
            throw new TratadorDeException.TratarErro400("Não foi possível reservar o livro: " + dto.livroIsbn());
        }
        var uri = uriComponentsBuilder.path("/reservas/{id}").buildAndExpand(reserva.id()).toUri();
        return ResponseEntity.created(uri).body(reserva);
    }

    // Endpoint para listar todas as reservas com paginação
    @GetMapping
    public ResponseEntity<Page<DTOReservaLista>> listar(
            @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var page = service.listarReservas(paginacao);
        return ResponseEntity.ok(page);
    }

    // Endpoint para cancelar uma reserva
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        var reserva = service.buscarReservaPorId(id);
        if (reserva == null) {
            throw new TratadorDeException.TratarErro404("Reserva não encontrada: " + id);
        }
        service.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para buscar uma reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<DTOReservaDetalhe> buscarPorId(@PathVariable Long id) {
        var reserva = service.buscarReservaPorId(id);
        if (reserva == null) {
            throw new TratadorDeException.TratarErro404("Reserva não encontrada: " + id);
        }
        return ResponseEntity.ok(reserva);
    }
}