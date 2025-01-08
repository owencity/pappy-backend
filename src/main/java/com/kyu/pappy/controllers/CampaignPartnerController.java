package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.CampaignPartnerDto;
import com.kyu.pappy.services.CampaignPartnerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
public class CampaignPartnerController {

    private final CampaignPartnerService campaignPartnerService;

    public CampaignPartnerController(CampaignPartnerService campaignPartnerService) {
        this.campaignPartnerService = campaignPartnerService;
    }

    @PostMapping("/create/{campaignId}")
    public CampaignPartnerDto savePartner(@PathVariable("campaignId") Long campaignId, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return campaignPartnerService.savePartner(username, campaignId);
    }

    @DeleteMapping("/delete")
    public void deletePartner(Long partnerId) {
        campaignPartnerService.deletePartner(partnerId);
    }
}
