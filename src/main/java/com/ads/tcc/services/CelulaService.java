package com.ads.tcc.services;

import com.ads.tcc.entities.celula.CadastroCelulaDTO;
import com.ads.tcc.entities.celula.Celula;
import com.ads.tcc.entities.user.Funcionario;
import com.ads.tcc.repositories.CelulaRepository;
import com.ads.tcc.repositories.FuncionarioRepository;
import com.ads.tcc.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CelulaService {

    @Autowired
    private CelulaRepository celulaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Celula inserir(CadastroCelulaDTO cadastroCelulaDTO){
        Celula celula = new Celula();
        celula.setNumero(cadastroCelulaDTO.numero());
        celula.setStatus(cadastroCelulaDTO.status());

        // Salva a célula para obter o ID gerado
        Celula savedCelula = celulaRepository.save(celula);

        // Associa a célula aos funcionários
        List<Funcionario> funcionarios = funcionarioRepository.findAllById(cadastroCelulaDTO.funcionarioIds());
        savedCelula.setFuncionarios(funcionarios);

        // Atualiza a célula com a lista de funcionários
        celulaRepository.save(savedCelula);

        // Associa o ID da célula aos funcionários
        for (Funcionario funcionario : funcionarios) {
            funcionario.setCelula(savedCelula);
        }

        // Atualiza os funcionários com a célula associada
        funcionarioRepository.saveAll(funcionarios);

        return savedCelula;
    }

    public List<Celula> listarTodos() {
        List<Celula> celulas = celulaRepository.findAll();
        return celulas;
    }

    public Celula buscarPorId(Long id) {
        return celulaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID não encontrado!")
        );
    }

    public Celula atualizar(Long id, Celula celula) {
        boolean exists = celulaRepository.existsById(id);
        if(exists){
            Celula entidade = celulaRepository.getReferenceById(id);
            editar(entidade, celula);
            return celulaRepository.save(entidade);
        }else{
            return null;
        }
    }

    private void editar(Celula entidade, Celula celula) {
        entidade.setNumero(celula.getNumero());
        entidade.setStatus(celula.getStatus());
    }

    public String deletar(Long id) {
        boolean exists = celulaRepository.existsById(id);
        if(exists){
            celulaRepository.deleteById(id);
            return "Célula deletada com sucesso!";
        }else{
            return "Célula não encontrada!";
        }
    }
}
