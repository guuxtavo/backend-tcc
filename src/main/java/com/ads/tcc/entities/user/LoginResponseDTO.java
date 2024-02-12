package com.ads.tcc.entities.user;

public record LoginResponseDTO(String token, UserDTO user) {
    public record UserDTO(Long id, String nome, String login, String role, String cargo, Boolean primeiroAcesso) {
        // Outras informações necessárias podem ser adicionadas conforme necessário
    }
}