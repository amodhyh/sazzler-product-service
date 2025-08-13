package com.sazzler.ecommerce.sazzler_productservice.Exceptions;

public class ProductNotFound extends RuntimeException {

    public ProductNotFound(String message) {
      super(message);
    }
}
