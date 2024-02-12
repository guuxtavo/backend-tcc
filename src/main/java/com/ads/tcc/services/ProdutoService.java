package com.ads.tcc.services;


import com.ads.tcc.entities.Produto;
import com.ads.tcc.services.exceptions.EntityNotFoundException;
import com.ads.tcc.repositories.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto inserir(Produto produto) {
        return repository.save(produto);
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID não encontrado! " + id)
        );
    }

    public Produto atualizar(Long id, Produto produto){
        Produto entidade = repository.getReferenceById(id);
        editar(entidade, produto);
        return repository.save(entidade);
    }

    public void editar(Produto entidade, Produto produto){
        entidade.setCor(produto.getCor());
        entidade.setModelo(produto.getModelo());
        entidade.setPontuacao(produto.getPontuacao());
        entidade.setTipo(produto.getTipo());
        entidade.setStatus(produto.getStatus());
        entidade.setProducoes(produto.getProducoes());
    }

    public String deletar(Long id) {
        Boolean existeId = repository.existsById(id);
        if(existeId){
            repository.deleteById(id);
            return "Produto deletado com sucesso!";
        }

        return "Produto não encontrado";
    }
}
