package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.CampaignDto;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.pagenation.PageResponse;
import com.kyu.pappy.model.story.StoryPatchRequestBody;
import com.kyu.pappy.security.CustomUserDetails;
import com.kyu.pappy.services.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public PageResponse<CampaignDto> getAllCampaign (
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return campaignService.getAllProductPaged(page, size);
    }

    @GetMapping("/{productId}")
    public CampaignDto getCampaignById (@PathVariable("productId") long productId) {

        return campaignService.getProductById(productId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCampaign(@RequestBody CampaignDto createDto) {
        try {
            CampaignDto savedProduct = campaignService.createProduct(createDto);
            return ResponseEntity.ok(savedProduct);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create product" + e.getMessage());
        }
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<CampaignDto> updateCampaign(@PathVariable("productId") Long productId, @RequestBody StoryPatchRequestBody storyPatchRequestBody, Authentication authentication) {

        CustomUserDetails  currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = currentUserDetails.getUsername();
        var updateProduct = campaignService.updateProduct(productId , storyPatchRequestBody, username);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteCampaign(@PathVariable Long productId, Authentication authentication) {

        campaignService.deleteProduct(productId, (User) authentication.getPrincipal());
    }
}
