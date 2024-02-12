package com.ads.tcc.controllers;

import com.ads.tcc.entities.celula.CadastroCelulaDTO;
import com.ads.tcc.entities.celula.Celula;
import com.ads.tcc.services.CelulaService;
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
@RequestMapping("/celulas")
public class CelulaController {

    @Autowired
    private CelulaService service;

    @PostMapping
    public ResponseEntity<Celula> inserir(@RequestBody CadastroCelulaDTO cadastroCelulaDTO) {
        System.out.println(cadastroCelulaDTO);
        Celula entidade = service.inserir(cadastroCelulaDTO);
        return ResponseEntity.status(201).body(entidade);
    }

    @GetMapping
    public ResponseEntity<List<Celula>> listarTodos() {
        List<Celula> celulas = service.listarTodos();
        return ResponseEntity.ok().body(celulas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Celula> buscarPorId(@PathVariable Long id) {
        Celula celula = service.buscarPorId(id);
        return ResponseEntity.ok().body(celula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Celula celula) {
        Celula entidadeAtualizada = service.atualizar(id, celula);
        if (entidadeAtualizada != null) {
            return ResponseEntity.ok().body(entidadeAtualizada);
        } else {
            return ResponseEntity.ok().body("ID NÃO ENCONTRADO!");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
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
