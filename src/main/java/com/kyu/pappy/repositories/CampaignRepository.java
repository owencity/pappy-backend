package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional <Campaign> findByProductName(String ProductName);
}
