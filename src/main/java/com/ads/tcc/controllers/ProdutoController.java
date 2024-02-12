package com.ads.tcc.controllers;


import com.ads.tcc.entities.Produto;
import com.ads.tcc.services.ProdutoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> inserir(@Valid @RequestBody Produto produto) {
        Produto obj = service.inserir(produto);
        return ResponseEntity.status(201).body(obj);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = service.listarTodos();
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = service.buscarPorId(id);
        return ResponseEntity.ok().body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@Valid @PathVariable Long id, @RequestBody Produto produto){
        Produto obj = service.atualizar(id, produto);
        return ResponseEntity.ok().body(obj);}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        String response = service.deletar(id);
        return ResponseEntity.ok().body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // quando ocorrer bad request
    @ExceptionHandler(MethodArgumentNotValidException.class) // será capturada essa exceção
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
