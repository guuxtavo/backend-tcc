package com.ads.tcc.repositories;

import com.ads.tcc.entities.celula.Celula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelulaRepository extends JpaRepository<Celula,Long> {
}
