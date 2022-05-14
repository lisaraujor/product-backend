package com.teste.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;

import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepository_old {
    
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    /**
     * Método para retornar uma lista de produtos
     * @return lista de produtos
     */
    public List<Produto> obterTodos(){
        return produtos;
    }

    /**
     * Método que retorna o produto encontrado pelo seu ID.
     * @param id do produto que será localizado
     * @return Retorna o produto caso seja encontrado
     */
    public Optional<Produto> obterPorId(Integer id){
        return produtos
                .stream()
                .filter(produto -> produto.getId() == id)
                .findFirst();
    }

    /**
     * Método para adicionar produto na lista.
     * @param produto que seá adicionado.
     * @return Retorna produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto){
        
        ultimoId++;;

        produto.setId(ultimoId);
        produtos.add(produto);

        return produto;
    }

    /**
     * Método para deletar o produto com o ID passado
     * @param id do produto a ser deletado.
     */
    public void deletar(Integer id){
        produtos.removeIf(produto -> produto.getId() == id);
    }

    /**
     * Método para atualizar produto na lista
     * @param produto que será atualizado
     * @return Retorna o produto atualizado na lista
     */
    public Produto atualizar(Produto produto){
        
        // Encontra o produto na lista
        Optional<Produto> produtoEncontrado = obterPorId(produto.getId());

        if(produtoEncontrado.isEmpty()){
            throw new ResourceNotFoundException("Produto não encontrado.");
        }
        
        // Remove produto antigo da lista
        deletar(produto.getId());

        // Adiciona novo produto atualizado na lista
        produtos.add(produto);

        return produto;

    }
}
