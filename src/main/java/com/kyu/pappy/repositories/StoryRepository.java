package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Story;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoryRepository extends JpaRepository<Story, Long> {

}
