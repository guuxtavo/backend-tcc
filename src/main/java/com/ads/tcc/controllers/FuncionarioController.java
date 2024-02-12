package com.ads.tcc.controllers;

import com.ads.tcc.entities.user.Funcionario;
import com.ads.tcc.services.FuncionarioService;
import jakarta.validation.Valid;
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
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos(){
        List<Funcionario> funcionarios = service.listarTodos();
        return ResponseEntity.ok().body(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable  Long id){
        Funcionario funcionario = service.buscarPorId(id);
        return ResponseEntity.ok().body(funcionario);
    }

    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<Funcionario>> buscarPorCargo(@PathVariable String cargo) {
        System.out.println("Chegou no controller de cargo");
        List<Funcionario> funcionarios = service.buscarPorCargo(cargo);
        return ResponseEntity.ok().body(funcionarios);
    }

    @PostMapping
    public ResponseEntity<Funcionario> inserir(@Valid @RequestBody Funcionario funcionario) {
        Funcionario obj = service.inserir(funcionario);
        return ResponseEntity.status(201).body(obj);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long id , @RequestBody Funcionario funcionario){
        Funcionario objeto = service.atualizar(id, funcionario);
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
