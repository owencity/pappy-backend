package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ProductDto;
import com.kyu.pappy.entities.Product;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.product.ProductPatchRequestBody;
import com.kyu.pappy.repositories.ProductRepository;
import com.kyu.pappy.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductDto> getAllProducts () {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto createDto) {
        try {
            ProductDto savedProduct = productService.createProduct(createDto);
            return ResponseEntity.ok(savedProduct);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create product" + e.getMessage());
        }
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductPatchRequestBody productPatchRequestBody , Authentication authentication) {

        var currentUser = (User) authentication.getPrincipal();
        var updateProduct = productService.updateProduct(productId , productPatchRequestBody, currentUser);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId, Authentication authentication) {

        productService.deleteProduct(productId, (User) authentication.getPrincipal());
    }
}
