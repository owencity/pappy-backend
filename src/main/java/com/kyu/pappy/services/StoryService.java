package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.StoryDto;
import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.pagenation.PageResponse;
import com.kyu.pappy.model.story.StoryPageResponse;
import com.kyu.pappy.model.story.StoryPatchRequestBody;
import com.kyu.pappy.repositories.StoryRepository;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;


    public StoryService(StoryRepository storyRepository, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
    }

    public PageResponse<StoryPageResponse> getStoryPaged(int page, int size) {
        List<Story> allCampaigns = storyRepository.findAll();

        List<StoryPageResponse> allStoryDto = allCampaigns.stream()
                .map(StoryPageResponse::from)
                .toList();

        return PaginationUtils.toPageResponse(allStoryDto, page, size);
    }

    @Transactional(readOnly = true)
    public StoryDto getStoryById (Long id) {
//        Story story = storyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));

        Story story = storyRepository.findByIdWithComments(id);
        return StoryDto.from(story);
    }

    public StoryDto createStory (StoryDto storyDto, String userEmail) {

        User findUser = userRepository.findByUserEmail(userEmail).orElseThrow(UserNotFoundException::new);

        Story createStory = StoryDto.to(storyDto, findUser);

        Story saveStory =  storyRepository.save(createStory);

        return StoryDto.from(saveStory);
    }

    @Transactional
    public StoryDto updateStory (Long StoryId, StoryPatchRequestBody storyPatchRequestBody, String currentUser) {

        Story findStory = storyRepository.findById(StoryId).orElseThrow( () -> new RuntimeException("Story not found"));
        userRepository.findByUserEmail(currentUser).orElseThrow(() -> new UserNotFoundException(currentUser));

        findStory.changeContent(storyPatchRequestBody.body());
        /*
            영속성 컨텍스트에서 변경감지 -> flush 시점에 UPDATE 쿼리

         */
        return StoryDto.from(findStory);
    }

    public void deleteStory (Long storyId, String currentUser) {
        Story findStory = storyRepository.findById(storyId).orElseThrow( () -> new RuntimeException("Product not found"));
        userRepository.findByUserEmail(currentUser).orElseThrow(() -> new UserNotFoundException(currentUser));
       storyRepository.delete(findStory);
    }
}
