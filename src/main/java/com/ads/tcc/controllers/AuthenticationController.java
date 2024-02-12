package com.ads.tcc.controllers;

import com.ads.tcc.configs.security.TokenService;
import com.ads.tcc.entities.user.AuthenticationDTO;
import com.ads.tcc.entities.user.Funcionario;
import com.ads.tcc.entities.user.LoginResponseDTO;
import com.ads.tcc.entities.user.RegisterDTO;
import com.ads.tcc.repositories.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        System.out.println("Entrou no login - LOGIN: " + data.login() + " - SENHA: " + data.password());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var principal = (Funcionario) auth.getPrincipal();

        var token = tokenService.generateToken(principal);

        System.out.println(token);

        LoginResponseDTO.UserDTO userDTO = new LoginResponseDTO.UserDTO(
                principal.getId(),
                principal.getNome(),
                principal.getLogin(),
                principal.getRole().getRole(),
                principal.getCargo(),
                principal.getPrimeiroAcesso()
                // Adicione outras informações conforme necessário
        );

        LoginResponseDTO responseDTO = new LoginResponseDTO(token, userDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        try {
            if (this.repository.findByLogin(data.login()) != null) {
                return ResponseEntity.badRequest().body("Login já existente");
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            Funcionario newFuncionario = new Funcionario(data.nome(), data.login(), encryptedPassword, data.classificacao(), data.cargo(), data.role(), true, data.dataNascimento());

            this.repository.save(newFuncionario);

            return ResponseEntity.ok().body("Funcionário cadastrado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar funcionário");
        }
    }

}
