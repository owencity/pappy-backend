package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.ProductDto;
import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.Region;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.pagenation.PageResponse;
import com.kyu.pappy.model.product.ProductPatchRequestBody;
import com.kyu.pappy.repositories.VolunteerRepository;
import com.kyu.pappy.repositories.ProductRepository;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public PageResponse<ProductDto> getAllProductPaged(int page, int size) {
        List<Campaign> allCampaigns = productRepository.findAll();

        List<ProductDto> allProductDto = allCampaigns.stream()
                .map(ProductDto::from)
                .toList();

        return PaginationUtils.toPageResponse(allProductDto, page, size);
    }

    public CampaignService(ProductRepository productRepository, VolunteerRepository volunteerRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.volunteerRepository = volunteerRepository;
        this.userRepository = userRepository;
    }

    public ProductDto getProductById (Long id) {
        Campaign campaign = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductDto.from(campaign);
    }

    public ProductDto createProduct (ProductDto dto) {

        Region region = volunteerRepository.findById(dto.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("category not found"));

        Campaign campaign = ProductDto.to(dto , region);

        Campaign saveCampaign =  productRepository.save(campaign);

        return ProductDto.from(saveCampaign);
    }

    @Transactional
    public ProductDto updateProduct (Long productId, ProductPatchRequestBody productPatchRequestBody, String currentUser) {



        Campaign findCampaign = productRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));
        userRepository.findByUserEmail(currentUser).orElseThrow(() -> new UserNotFoundException(currentUser));

        findCampaign.changeContent(productPatchRequestBody.body());
        /*
            영속성 컨텍스트에서 변경감지 -> flush 시점에 UPDATE 쿼리

         */
        return ProductDto.from(findCampaign);
    }

    public void deleteProduct (Long productId, User currentUser) {
        Campaign findCampaign = productRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));

        productRepository.delete(findCampaign);
    }
}
