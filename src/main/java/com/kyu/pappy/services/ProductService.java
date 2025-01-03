package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotAllowException;
import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.CategoryDto;
import com.kyu.pappy.dtos.ProductDto;
import com.kyu.pappy.entities.Category;
import com.kyu.pappy.entities.Product;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.pagenation.PageResponse;
import com.kyu.pappy.model.product.ProductPatchRequestBody;
import com.kyu.pappy.repositories.CategoryRepository;
import com.kyu.pappy.repositories.ProductRepository;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository  productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public PageResponse<ProductDto> getAllProductPaged(int page, int size) {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDto> allProductDto = allProducts.stream()
                .map(ProductDto::from)
                .toList();

        return PaginationUtils.toPageResponse(allProductDto, page, size);
    }

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public ProductDto getProductById (Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductDto.from(product);
    }

    public ProductDto createProduct (ProductDto dto) {

        Category category = categoryRepository.findById(dto.categoryId().longValue())
                .orElseThrow(() -> new IllegalArgumentException("category not found"));

        Product product = ProductDto.to(dto , category);

       Product saveProduct =  productRepository.save(product);

       return ProductDto.from(saveProduct);
    }

    @Transactional
    public ProductDto updateProduct (Long productId, ProductPatchRequestBody productPatchRequestBody, String currentUser) {



        Product findProduct = productRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));
        userRepository.findByUserEmail(currentUser).orElseThrow(() -> new UserNotFoundException(currentUser));

        findProduct.changeContent(productPatchRequestBody.body());

        /*
            영속성 컨텍스트에서 변경감지 -> flush 시점에 UPDATE 쿼리

         */
        return ProductDto.from(findProduct);
    }

    public void deleteProduct (Long productId, User currentUser) {
        Product findProduct = productRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));

        productRepository.delete(findProduct);
    }
}
