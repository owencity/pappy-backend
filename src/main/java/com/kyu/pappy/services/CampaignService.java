package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.CampaignDto;
import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.pagenation.PageResponse;
import com.kyu.pappy.model.story.StoryPatchRequestBody;
import com.kyu.pappy.repositories.CampaignRepository;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public CampaignService(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    public PageResponse<CampaignDto> getAllCampaignPaged(int page, int size) {
        List<Campaign> allCampaigns = campaignRepository.findAll();

        List<CampaignDto> allCampaignDto = allCampaigns.stream()
                .map(CampaignDto::from)
                .toList();

        return PaginationUtils.toPageResponse(allCampaignDto, page, size);
    }



    public CampaignDto getCampaignById (Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return CampaignDto.from(campaign);
    }

    public CampaignDto createCampaign (CampaignDto dto) {



        Campaign campaign = CampaignDto.to(dto);

       Campaign saveCampaign =  campaignRepository.save(campaign);

       return CampaignDto.from(saveCampaign);
    }

    @Transactional
    public CampaignDto updateCampaign (Long productId, StoryPatchRequestBody storyPatchRequestBody, String currentUser) {



        Campaign findCampaign = campaignRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));
        userRepository.findByUserEmail(currentUser).orElseThrow(() -> new UserNotFoundException(currentUser));

        findCampaign.changeContent(storyPatchRequestBody.body());
        /*
            영속성 컨텍스트에서 변경감지 -> flush 시점에 UPDATE 쿼리

         */
        return CampaignDto.from(findCampaign);
    }

    public void deleteCampaign (Long productId, User currentUser) {
        Campaign findCampaign = campaignRepository.findById(productId).orElseThrow( () -> new RuntimeException("Product not found"));

        campaignRepository.delete(findCampaign);
    }
}
