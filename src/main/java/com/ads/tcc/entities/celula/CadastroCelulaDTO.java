package com.ads.tcc.entities.celula;

import java.util.List;

public record CadastroCelulaDTO(Integer numero, String status, List<Long> funcionarioIds) {

}
