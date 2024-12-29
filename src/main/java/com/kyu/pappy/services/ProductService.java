package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotAllowException;
import com.kyu.pappy.dtos.ProductDto;
import com.kyu.pappy.entities.Product;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.product.ProductPatchRequestBody;
import com.kyu.pappy.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {

    private final ProductRepository  productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProducts (Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductDto.from(product);
    }

    public ProductDto createProduct (ProductDto dto) {
       Product saveProduct =  productRepository.save(ProductDto.to(dto));
       return ProductDto.from(saveProduct);
    }

    @Transactional
    public ProductDto updateProduct (Long productId, ProductPatchRequestBody productPatchRequestBody, User currentUser) {
        Product findProduct = productRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));
    if(!findProduct.getUser().equals(currentUser)) {
        throw new UserNotAllowException();
    }
        findProduct.changeContent(productPatchRequestBody.body());

        /*
            영속성 컨텍스트에서 변경감지 -> flush 시점에 UPDATE 쿼리

         */
        return ProductDto.from(findProduct);
    }

    public void deleteProduct (Long productId, User currentUser) {
        Product findProduct = productRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));
        if(!findProduct.getUser().equals(currentUser)) {
            throw new UserNotAllowException();
        }
        productRepository.delete(findProduct);
    }
}
