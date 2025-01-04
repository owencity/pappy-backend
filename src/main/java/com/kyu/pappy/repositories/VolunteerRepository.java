package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Region, Long> {

    boolean existsByCategoryName(String categoryName);

}
