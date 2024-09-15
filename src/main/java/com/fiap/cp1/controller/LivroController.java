package com.fiap.cp1.controller;

import com.fiap.cp1.livro.*;
import com.fiap.cp1.Exceptions.TratadorDeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("livros")
public class LivroController {

    @Autowired
    private LivroRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity Cadastrar(@RequestBody @Valid DTOCadastroLivro dados, UriComponentsBuilder uriComponentsBuilder) {
        if (repository.existsByIsbn(dados.getIsbn())) {
            throw new TratadorDeException.TratarErro400("ISBN já cadastrado: " + dados.getIsbn());
        }

        var livro = new Livro(dados);
        repository.save(livro);
        var uri = uriComponentsBuilder.path("/livros/{isbn}").buildAndExpand(livro.getIsbn()).toUri();
        return ResponseEntity.created(uri).body(new DTODetalheLivro(livro));
    }

    @GetMapping
    public ResponseEntity<Page<DTOListaLivro>> listar(
            @PageableDefault(size = 10, sort = {"autor", "titulo"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DTOListaLivro::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @DeleteMapping("/{isbn}")
    public ResponseEntity Excluir(@PathVariable Long isbn) {
        var livro = repository.findByIsbn(isbn)
            .orElseThrow(() -> new TratadorDeException.TratarErro404("Livro não encontrado: " + isbn));
        livro.Excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity BuscarPorIsbn(@PathVariable Long isbn) {
        var livro = repository.findByIsbn(isbn)
            .orElseThrow(() -> new TratadorDeException.TratarErro404("Livro não encontrado: " + isbn));
        return ResponseEntity.ok(new DTODetalheLivro(livro));
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<Page<DTOListaLivro>> listarPorAutor(
            @PathVariable String autor,
            @PageableDefault(size = 10, sort = {"autor", "titulo"}) Pageable paginacao) {
        var page = repository.findByAutorContainingIgnoreCaseAndAtivoTrue(autor, paginacao).map(DTOListaLivro::new);
        return page.isEmpty() ?
               ResponseEntity.notFound().build() :
               ResponseEntity.ok(page);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<DTOListaLivro>> listarPorTitulo(
            @PathVariable String titulo, @PageableDefault(size = 10, sort = {"autor", "titulo"}) Pageable paginacao) {
        titulo = titulo.replace("+", " ");
        var page = repository.findByTituloContainingIgnoreCaseAndAtivoTrue(titulo, paginacao).map(DTOListaLivro::new);
        return page.isEmpty() ?
               ResponseEntity.notFound().build() :
               ResponseEntity.ok(page);
    }
}


