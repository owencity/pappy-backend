package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Story;

import java.util.List;

public interface StoryRepositoryCustom {
    Story findByIdWithComments(Long id);
    List<Story> findStoryPagination( int page , int size);
}
