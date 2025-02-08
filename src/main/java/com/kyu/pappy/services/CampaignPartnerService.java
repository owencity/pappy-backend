package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.CampaignPartnerDto;
import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.CampaignPartner;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.repositories.CampaignPartnerRepository;
import com.kyu.pappy.repositories.CampaignRepository;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
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

    public CampaignPartnerDto savePartner(Authentication auth, Long campaignId) {

        // 받은 jwt로 CustomuserDetails 통해 context에있는 user정보 불러와서 username 저장
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String userEmail = userDetails.getUsername();
        // 중복되지않았으면 user정보 반환 , campaign 동일
        User findUser = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UserNotFoundException("user not found" + userEmail));
        Campaign findCampaign = campaignRepository.findById(campaignId).orElseThrow(() -> new RuntimeException("campaign not found"));

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
