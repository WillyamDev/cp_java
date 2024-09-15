package com.fiap.cp1.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestControllerAdvice
public class TratadorDeException {

    // Exceção para erro 404 (não encontrado)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class TratarErro404 extends RuntimeException {
        public TratarErro404(String message) {
            super(message);
        }
    }

    // Exceção para erro 400 (bad request)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class TratarErro400 extends RuntimeException {
        public TratarErro400(String message) {
            super(message);
        }
    }

    // Exceção para conflito de cadastro (Livro já cadastrado)
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class LivroJaCadastradoException extends RuntimeException {
        public LivroJaCadastradoException(String message) {
            super(message);
        }
    }

    // Tratamento de MethodArgumentNotValidException (dados inválidos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    // Manipulador da exceção TratarErro404
    @ExceptionHandler(TratarErro404.class)
    public ResponseEntity<String> handleNotFound(TratarErro404 ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Manipulador da exceção TratarErro400
    @ExceptionHandler(TratarErro400.class)
    public ResponseEntity<String> handleBadRequest(TratarErro400 ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Manipulador da exceção LivroJaCadastradoException
    @ExceptionHandler(LivroJaCadastradoException.class)
    public ResponseEntity<String> handleLivroJaCadastrado(LivroJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}