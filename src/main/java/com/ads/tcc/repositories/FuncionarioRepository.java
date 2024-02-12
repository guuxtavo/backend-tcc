package com.ads.tcc.repositories;

import com.ads.tcc.entities.user.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    UserDetails findByLogin(String login);

//    @Query("SELECT f FROM funcionarios f WHERE f.celula_id IS EMPTY AND f.role != 'ADMIN' AND f.cargo = :cargo")
//    List<Funcionario> findByCargo(@Param("cargo") String cargo);

    @Query("FROM funcionarios f WHERE f.cargo = :cargo")
    List<Funcionario> buscarPorCargo( String cargo);
}
