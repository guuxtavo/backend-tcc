package com.ads.tcc.entities.user;

import java.util.Date;

public record RegisterDTO(String nome, String login, String password, String classificacao, String cargo, UserRole role, String dataNascimento) {
}
