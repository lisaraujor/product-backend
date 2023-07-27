package com.teste.primeiroexemplo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.primeiroexemplo.model.Product;
import com.teste.primeiroexemplo.services.ProductService;
import com.teste.primeiroexemplo.shared.ProductDTO;
import com.teste.primeiroexemplo.view.model.ProductRequest;
import com.teste.primeiroexemplo.view.model.ProductResponse;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(){
        List<ProductDTO> products = productService.getAll();
        
        ModelMapper mapper = new ModelMapper();

        List<ProductResponse> response = products.stream()
        .map(productDto -> mapper.map(productDto, ProductResponse.class))
        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
        }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductResponse>> getById(@PathVariable Integer id){
      
        // try{        
        Optional<ProductDTO> dto = productService.getById(id);
        ProductResponse product =  new ModelMapper().map(dto.get(), ProductResponse.class);
        return new ResponseEntity<>(Optional.of(product), HttpStatus.OK);
        // } catch (Exception e){
        //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // }
    }

    @PostMapping
    public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest productReq){
        ModelMapper mapper = new ModelMapper();

        ProductDTO productDto = mapper.map(productReq, ProductDTO.class);
        
        productDto = productService.add(productDto);

        return new ResponseEntity<>(mapper.map(productDto, ProductResponse.class), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@RequestBody ProductRequest productReq, @PathVariable Integer id){
        
        ModelMapper mapper = new ModelMapper();
        ProductDTO productDto = mapper.map(productReq, ProductDTO.class);

        productDto = productService.update(id, productDto);
        
        return new ResponseEntity<>(
            mapper.map(productDto, ProductResponse.class),
            HttpStatus.OK);
    }

}
