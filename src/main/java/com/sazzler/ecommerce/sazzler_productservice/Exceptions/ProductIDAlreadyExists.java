package com.sazzler.ecommerce.sazzler_productservice.Exceptions;

public class ProductIDAlreadyExists extends RuntimeException {
    public ProductIDAlreadyExists(String message) {
        super(message);
    }
}
