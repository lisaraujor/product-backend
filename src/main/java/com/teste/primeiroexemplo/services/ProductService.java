package com.teste.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiroexemplo.model.Product;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiroexemplo.repository.ProductRepository;
import com.teste.primeiroexemplo.shared.ProductDTO;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAll(){

        List<Product> products = productRepository.findAll();

        return products.stream()
        .map(product -> new ModelMapper().map(product, ProductDTO.class))
        .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getById(Integer id){
        // Getting product optional by id
        Optional<Product> product = productRepository.findById(id);
        
        // If not find, throw exception
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product with id: " + id + " not find");

        }
        // Converting my product optional into a productDTO
        ProductDTO dto = new ModelMapper().map(product.get(), ProductDTO.class);

        // Creating and returning a productDTO optional
        return Optional.of(dto);
    }

    public ProductDTO add(ProductDTO productDto){
        // Removing the id to be able to register
        productDto.setId(null);

        // Create a mapping object
        ModelMapper mapper = new ModelMapper();

        // Convert the ProductDTO to a Product
        Product product = mapper.map(productDto,Product.class);

        // Save Product in database
        product = productRepository.save(product);

        productDto.setId(product.getId());
        
        // Return the updated ProductDTO
        return productDto;
    }

    public void delete(Integer id){
        // Verify that product exists
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            throw new ResourceNotFoundException("It was not possible delete the product with id: " + id);
        }

        // Delete by id
        productRepository.deleteById(id);
    }

    public ProductDTO update(Integer id, ProductDTO productDto){
        
        // Pass id to produtoDto
        productDto.setId(id);

        // Create a mapping object
        ModelMapper mapper = new ModelMapper();

        // Convert the ProductDTO to a Product
        Product product = mapper.map(productDto, Product.class);

        // Update Product in database
        productRepository.save(product);

        // Returns updated produtoDto
        return productDto;

    }
}
