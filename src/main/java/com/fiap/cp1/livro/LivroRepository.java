package com.fiap.cp1.livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Page<Livro> findAllByAtivoTrue(Pageable paginacao);

    Livro getReferenceByIsbn(Long isbn);

    Page<Livro> findByAutorContainingIgnoreCaseAndAtivoTrue(String autor, Pageable pageable);

    Page<Livro> findByTituloContainingIgnoreCaseAndAtivoTrue(String titulo, Pageable pageable);
}

