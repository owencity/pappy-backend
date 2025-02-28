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
@RequestMapping("/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService)   {
        this.campaignService = campaignService;
    }

    @GetMapping
    public PageResponse<CampaignDto> getAllCampaign (
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return campaignService.getAllCampaignPaged(page, size);
    }

    @GetMapping("/{campaignId}")
    public CampaignDto getCampaignById (@PathVariable("campaignId") long campaignId) {
        return campaignService  .getCampaignById(campaignId);
    }

    @PostMapping("/create")
    public CampaignDto createCampaign(@RequestBody CampaignDto createDto) {
            return campaignService.createCampaign(createDto);
    }

    @PatchMapping("/update/{campaignId}")
    public ResponseEntity<CampaignDto> updateCampaign(
            @PathVariable("campaignId") Long productId,
            @RequestBody StoryPatchRequestBody storyPatchRequestBody,
            Authentication authentication) {

        var updateProduct = campaignService.updateCampaign(productId , storyPatchRequestBody, authentication);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/delete/{campaignId}")
    public void deleteCampaign(@PathVariable Long campaignId, Authentication authentication) {
        campaignService.deleteCampaign(campaignId,authentication);
    }
}
