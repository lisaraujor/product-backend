package com.teste.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiroexemplo.repository.ProdutoRepository;
import com.teste.primeiroexemplo.shared.ProdutoDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> obterTodos(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
        .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
        .collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> obterPorId(Integer id){
        //Obtendo optional de produto pelo id
        Optional<Produto> produto = produtoRepository.findById(id);
        
        // Se não encontrar, lança exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Produto com id: " + id + " não encontrado");

        }
        // Convertendo o meu optional de produto em um produtoDTO
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        // Criando e retornando um optional de produtoDTO.
        return Optional.of(dto);
    }

    public ProdutoDTO adicionar(ProdutoDTO produtoDto){
        //Removendo o id para conseguir fazer o cadastro.
        produtoDto.setId(null);

        //Criar um objeto de mapeamento.
        ModelMapper mapper = new ModelMapper();

        // Converter o produtoDTO em um Produto
        Produto produto = mapper.map(produtoDto,Produto.class);

        // Salvar o Produto do banco
        produto = produtoRepository.save(produto);

        produtoDto.setId(produto.getId());
        
        //Retornar o ProdutoDTO atualizado.
        return produtoDto;
    }

    public void deletar(Integer id){
        //Verificar que o produto existe
        Optional<Produto> produto = produtoRepository.findById(id);

        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Não foi possível deletar o produto com o id: " + id);
        }

        //Deleta pelo id
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto){
        
        //Passar o id para o produtoDto
        produtoDto.setId(id);

        //Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();

        //Converter o ProdutoDTO em um Produto
        Produto produto = mapper.map(produtoDto, Produto.class);

        // Atualizar o produto no Banco de dados
        produtoRepository.save(produto);

        //Retorna produtoDto atualizado
        return produtoDto;

    }


}
