package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.StoryDto;
import com.kyu.pappy.model.story.StoryPageResponse;
import com.kyu.pappy.model.story.StoryPatchRequestBody;
import com.kyu.pappy.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public List<StoryPageResponse> getAllStory (
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        return storyService.getStoryPaged(page, size);
    }

    @GetMapping("/{storyId}")
    public StoryDto getStoryById (@PathVariable("storyId") long storyId) {
        return storyService.getStoryById(storyId);
    }

    @PostMapping("/create")
    public StoryDto createStory(@RequestBody StoryDto createDto, Authentication auth) {
            return storyService.createStory(createDto, auth);
    }

    @PatchMapping("/update/{storyId}")
    public ResponseEntity<StoryDto> updateStory(@PathVariable("storyId") Long storyId, @RequestBody StoryPatchRequestBody storyPatchRequestBody , Authentication auth) {
        var updateStory = storyService.updateStory(storyId , storyPatchRequestBody, auth);
        var updates = updateStory;
        return ResponseEntity.ok(updateStory);

    }

    @DeleteMapping("/delete/{storyId}")
    public void deleteStory(@PathVariable("storyId") Long storyId, Authentication auth) {
        storyService.deleteStory(storyId, auth);
    }
}
