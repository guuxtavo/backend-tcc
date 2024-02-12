package com.ads.tcc.services;

import com.ads.tcc.entities.Producao;
import com.ads.tcc.entities.Produto;
import com.ads.tcc.repositories.ProducaoRepository;
import com.ads.tcc.repositories.ProdutoRepository;
import com.ads.tcc.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducaoService {
    @Autowired
    private ProducaoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Producao inserir(Producao producao) {
        boolean exists = producao.getProdutos().stream().allMatch(p -> produtoRepository.existsById(p.getId()));
        if(exists){
            System.out.println("Entrou no IF do exists");
            return repository.save(producao);
        }else{
            System.out.println("Entrou no else");
            return null;
        }
    }

    public List<Producao> listarTodos() {
        return repository.findAll();
    }

    public Producao buscarPorId(Long id){
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID não encontrado: " + id)
        );
    }

    public Producao atualizar(Long id, Producao producao){
        Producao entidade = repository.getReferenceById(id);
        editar(entidade, producao);
        return repository.save(entidade);
    }

    public void editar(Producao entidade, Producao producao){
        entidade.setData(producao.getData());
        entidade.setPontuacao(producao.getPontuacao());
        entidade.setValorComissao(producao.getValorComissao());
        entidade.setStatus(producao.getStatus());
        entidade.setObservacao(producao.getObservacao());
        entidade.setProdutos(producao.getProdutos());
        entidade.setFuncionarios(producao.getFuncionarios());
    }

    public String deletar(Long id) {
        Boolean existeId = repository.existsById(id);
        if(existeId){
            repository.deleteById(id);
            return "Produção deletada com sucesso!";
        }

        return "Produção não encontrada!";
    }
}
