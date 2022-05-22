package com.nttdata.bankingInquiries.controller;

import java.util.List;
import java.util.Optional;

import com.nttdata.bankingInquiries.entity.Product;
import com.nttdata.bankingInquiries.repository.ProductRepository;
import com.nttdata.bankingInquiries.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepo;
    //CRUD
    @GetMapping(value = "/all")
    public List<Product> getAll() {
        return productService.getAll();
    } 

    @PostMapping(value = "/create")
    public Product createProduct(@RequestBody Product new_produc){
        new_produc.setStatus("ACTIVE");
        return productService.createProduct(new_produc);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product temp) {
      Optional<Product> product = productRepo.findById(id);
      if (product.isPresent()) {
        temp.setId(id);
        return new ResponseEntity<>(productService.createProduct(temp), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @PutMapping("setInactive/{id}")
    public ResponseEntity<Product> setInactive(@PathVariable("id") String id) {
      Optional<Product> product_dov = productRepo.findById(id);
      if (product_dov.isPresent()) {
        Product _product = product_dov.get();
        _product.setStatus("INACTIVE");
        return new ResponseEntity<>(productRepo.save(_product), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } 


}
