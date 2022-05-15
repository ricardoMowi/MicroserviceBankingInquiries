package com.nttdata.bankingInquiries.service;

import java.util.List;

import com.nttdata.bankingInquiries.entity.Product;

public interface ProductService {
    List<Product> getAll();
    Product createProduct(Product new_product);
}
