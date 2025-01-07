package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.CampaignPartnerDto;
import com.kyu.pappy.services.CampaignPartnerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
public class CampaignPartnerController {

    private final CampaignPartnerService campaignPartnerService;

    public CampaignPartnerController(CampaignPartnerService campaignPartnerService) {
        this.campaignPartnerService = campaignPartnerService;
    }

    @PostMapping("/create")
    public CampaignPartnerDto savePartner(@RequestBody CampaignPartnerDto campaignPartnerDto) {
        return campaignPartnerService.savePartner(campaignPartnerDto, campaignPartnerDto.userId(), campaignPartnerDto.campaignId());
    }

    @DeleteMapping("/delete")
    public void deletePartner(Long partnerId) {
        campaignPartnerService.deletePartner(partnerId);
    }
}
