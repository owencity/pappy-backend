package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ProductDto;
import com.kyu.pappy.entities.Product;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.pagenation.PageResponse;
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

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PageResponse<ProductDto> getAllProducts (
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getAllProductPaged(page, size);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById (@PathVariable long productId) {

        return productService.getProductById(productId);
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
