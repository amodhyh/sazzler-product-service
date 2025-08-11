package com.sazzler.ecommerce.sazzler_productservice.Repository;

import com.sazzler.ecommerce.sazzler_productservice.Entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends MongoRepository<Product,String> {
    Product findByName(String name);
}
