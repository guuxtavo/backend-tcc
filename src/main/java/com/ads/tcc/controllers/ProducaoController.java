package com.ads.tcc.controllers;

import com.ads.tcc.entities.Producao;
import com.ads.tcc.services.ProducaoService;
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
@RequestMapping("/producao")
public class ProducaoController {

    @Autowired
    private ProducaoService service;

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Producao producao) {
        Producao objeto = service.inserir(producao);
        if (objeto != null) {
            return ResponseEntity.ok().body(objeto);
        } else {
            return new ResponseEntity<String>("ID do produto não encotrado", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public ResponseEntity<List<Producao>> listarTodos() {
        List<Producao> producoes = service.listarTodos();
        return ResponseEntity.ok().body(producoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producao> buscarPorId(@PathVariable Long id) {
        Producao objeto = service.buscarPorId(id);
        return ResponseEntity.ok().body(objeto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producao> atualizar(@PathVariable Long id,@RequestBody Producao producao) {
        Producao objeto = service.atualizar(id, producao);
        return ResponseEntity.ok().body(objeto);
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
