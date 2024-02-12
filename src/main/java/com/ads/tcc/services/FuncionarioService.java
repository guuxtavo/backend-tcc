package com.ads.tcc.services;

import com.ads.tcc.entities.user.Funcionario;
import com.ads.tcc.repositories.FuncionarioRepository;
import com.ads.tcc.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public Funcionario inserir(Funcionario funcionario){
        return repository.save(funcionario);
    }

    public List<Funcionario> listarTodos(){
        List<Funcionario> funcionarios = repository.findAll();
        return funcionarios;
    }

    public Funcionario buscarPorId(Long id){
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID não encontrado : " + id)
        );
    }

    public Funcionario atualizar(Long id, Funcionario funcionario){
        boolean exists = repository.existsById(id);
        if(exists){
            Funcionario entidade = repository.getReferenceById(id);
            editar(entidade, funcionario);
            return repository.save(entidade);
        }else{
            return null;
        }
    }

    public void editar(Funcionario entidade, Funcionario funcionario){
        entidade.setLogin(funcionario.getLogin());
        entidade.setCargo(funcionario.getCargo());
        entidade.setNome(funcionario.getNome());
        entidade.setClassificacao(funcionario.getClassificacao());
        entidade.setSenha(funcionario.getSenha());

    }

    public String deletar(Long id) {
        boolean exists = repository.existsById(id);
        if(exists){
            repository.deleteById(id);
            return "Funcionário deletado com sucesso!";
        }else{
            return "Funcionário não encontrado!";
        }
    }

    public List<Funcionario> buscarPorCargo(String cargo) {
        System.out.println("Chegou no service de cargo");
        return repository.buscarPorCargo(cargo);
    }
}
