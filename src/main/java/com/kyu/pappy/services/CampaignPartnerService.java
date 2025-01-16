package com.kyu.pappy.services;

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

        // 중복되지않았으면 user정보 반환 , campaign 동일
        User findUser = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new RuntimeException("user_id Not found"));
        Campaign findCampaign = campaignRepository.findById(campaignId).orElseThrow(() -> new RuntimeException("user_id Not found"));

        // 중복참여자 확인 위한 exception 코드
        if(campaignPartnerRepository.existsByUserIdAndCampaignId(findUser.getId(), findCampaign.getId())) {
            throw new RuntimeException("This user has already joined this campaign");
        }

        // 다대다 테이블에 찾은 user , campaign 저장
        CampaignPartner saveCampaignPartner = campaignPartnerRepository.save(CampaignPartnerDto.to(findUser, findCampaign));
        return CampaignPartnerDto.from(saveCampaignPartner);
    }

    public void deletePartner(Long partnerId) {
        campaignPartnerRepository.deleteById(partnerId);
    }
}
