package com.ads.tcc.entities.user;

import com.ads.tcc.entities.celula.Celula;
import com.ads.tcc.entities.Producao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(name = "funcionarios")
@Table(name = "tb_funcionario")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Funcionario implements UserDetails, Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotBlank(message = "O campo 'nome' não pode ser vazio!")
    private String nome;

    @CPF(message = "CPF Inválido!")
    @NotBlank(message = "O campo 'Login' não pode ser vazio!")
    private String login;

    @NotBlank(message = "O campo 'senha' não pode ser vazio!")
    private String senha;

    @NotBlank(message = "O campo 'classificacao' não pode ser vazio!")
    private String classificacao;

    @NotBlank(message = "O campo 'dataNascimento' não pode ser vazio")
    private String dataNascimento;

    @NotBlank(message = "O campo 'cargo' não pode ser vazio!")
    private String cargo;

    private UserRole role;

    private Boolean primeiroAcesso;

    @JsonIgnore
    @ManyToMany(mappedBy = "funcionarios")
    private List<Producao> producoes;

    @ManyToOne
    @JoinColumn(name = "celula_id")
    private Celula celula;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "funcionarios")
//    private List<Celula> celulas;
//
//    public Funcionario(String login, String senha, UserRole role) {
//        this.login = login;
//        this.senha = senha;
//        this.role = role;
//    }

    public Funcionario(String nome, String cpf, String senha, String classificacao, String cargo, UserRole role, Boolean primeiroAcesso, String dataNascimento) {
        this.nome = nome;
        this.login = cpf;
        this.senha = senha;
        this.classificacao = classificacao;
        this.cargo = cargo;
        this.role = role;
        this.dataNascimento = dataNascimento;
        this.primeiroAcesso = primeiroAcesso;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) {
            return Collections.emptyList(); // Retorna uma lista vazia se a role for nula
        }

        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
