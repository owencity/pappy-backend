package com.kyu.pappy.services;

import com.kyu.pappy.dtos.CampaignDto;
import com.kyu.pappy.dtos.CampaignPartnerDto;
import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.CampaignPartner;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.repositories.CampaignPartnerRepository;
import com.kyu.pappy.repositories.CampaignRepository;
import com.kyu.pappy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CampaignPartnerService {

    private final CampaignPartnerRepository campaignPartnerRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;

    public CampaignPartnerService(CampaignPartnerRepository campaignPartnerRepository, UserRepository userRepository, CampaignRepository campaignRepository) {
        this.campaignPartnerRepository = campaignPartnerRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
    }

    public CampaignPartnerDto savePartner( String userEmail, Long campaignId) {

        User findUser = userRepository.findByUserEmail(userEmail).orElse(null);
        Campaign findCampaign = campaignRepository.findById(campaignId).orElse(null);


        CampaignPartner saveCampaignPartner = campaignPartnerRepository.save(CampaignPartnerDto.to(findUser, findCampaign));

        return CampaignPartnerDto.from(saveCampaignPartner);
    }

    public void deletePartner(Long partnerId) {
        campaignPartnerRepository.deleteById(partnerId);
    }
}
