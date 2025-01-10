package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Story;

public interface StoryRepositoryCustom {
    Story findByIdWithComments(Long id);

}
