package com.teste.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.teste.primeiroexemplo.model.Product;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;

@Repository
public class ProdutoRepository_old {
    
    private ArrayList<Product> products = new ArrayList<Product>();
    private Integer lastId = 0;

    /**
     * Method to return product list
     * @return products list
     */
    public List<Product> getAll(){
        return products;
    }

    /**
     * Method to return product by id
     * @param id product
     * @return Return a product if found
     */
    public Optional<Product> getById(Integer id){
        return products
                .stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    /**
     * Method to add a product to list
     * @param product that will be added
     * @return Returns added product
     */
    public Product addProduct(Product product){
        
        lastId++;;

        product.setId(lastId);
        products.add(product);

        return product;
    }

    /**
     * Method to delete the product with the ID passed
     * @param id product
     */
    public void delete(Integer id){
        products.removeIf(product -> product.getId() == id);
    }

    /**
     * Method to update list
     * @param product that will be updated
     * @return Returns updated product in the list
     */
    public Product update(Product product){
        
        // Find product in the list
        Optional<Product> productFound = getById(product.getId());

        if(productFound.isEmpty()){
            throw new ResourceNotFoundException("Product not find.");
        }
        
        // Remove old product from list
        delete(product.getId());

        // Add new updated product in list
        products.add(product);

        return product;

    }
}
