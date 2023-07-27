package com.teste.primeiroexemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.primeiroexemplo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
