package com.ads.tcc.configs.security;

import com.ads.tcc.entities.user.Funcionario;
import com.ads.tcc.repositories.FuncionarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    // Será chamado antes
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtro de segurança interceptou a requisição");
        var token = this.recoverToken(request);
        if(token != null){
            var login = tokenService.validateToken(token);
            UserDetails user = funcionarioRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (isTokenExpiringSoon(token)) {
                // Gere um novo token e adicione-o à resposta
                String newToken = tokenService.generateToken((Funcionario) user); // Certifique-se de incluir as informações necessárias
                response.setHeader("X-New-Token", newToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTokenExpiringSoon(String token) {
        Instant expirationDate = tokenService.genExpirationDate(); // Obtenha a data de expiração do token
        Instant currentTime = Instant.now();

        // Verifique se o token está prestes a expirar (por exemplo, nos próximos 5 minutos)
        return expirationDate.minusSeconds(300).isBefore(currentTime);
    }


    private String recoverToken(HttpServletRequest request){

        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", ""); // Remova espaços extras aqui
    }
}
