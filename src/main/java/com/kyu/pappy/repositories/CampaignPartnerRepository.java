package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.CampaignPartner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignPartnerRepository extends JpaRepository<CampaignPartner, Long> {
    boolean existsByUserIdAndCampaignId(Long findUserId, Long findCampaignId);
}
