package com.kyu.pappy.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.StoryDto;
import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.story.StoryPageResponse;
import com.kyu.pappy.model.story.StoryPatchRequestBody;
import com.kyu.pappy.repositories.StoryRepository;
import com.kyu.pappy.repositories.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public StoryService(StoryRepository storyRepository, UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<StoryPageResponse> getStoryPaged(int page, int size) {
        List<Story> storyPage =  storyRepository.findStoryPagination(page, size);
        // 데이터베이스에서 페이지네이션된 Story 엔티티 리스트를 가져옴

        return storyPage.stream()
                .map(StoryPageResponse::from)
                .toList();
        // 각 stream의 각 요소(story)를 StoryPageResponse로 변환.
        // from 메더드는 story 객체를 받아 storyPageResponse로 변환 하는 정적메서드
        // 변환된 stream을 Lis로 변환 최종결과 List<StoryPageResponse)
    }

    public StoryDto getStoryById (Long id)  {

//        Story story = storyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
        String cacheKey = "story:" + id;

        // Redis에서 캐시 데이터 조회
        Object cachedData = redisTemplate.opsForValue().get(cacheKey);

        if (cachedData != null) {
            // LinkedHashMap을 StoryDto로 변환
            if (cachedData instanceof LinkedHashMap) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime 처리
                return objectMapper.convertValue(cachedData, StoryDto.class);
            }
            return (StoryDto) cachedData; // 이미 StoryDto 타입이라면 바로 반환
        }

        // 캐시에 데이터가 없으면 DB 조회
        Story story = storyRepository.findByIdWithComments(id);

// DTO로 변환 및 캐싱
        StoryDto storyDto = StoryDto.from(story);
        redisTemplate.opsForValue().set(cacheKey, storyDto, 10, TimeUnit.MINUTES);

        return storyDto;
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
