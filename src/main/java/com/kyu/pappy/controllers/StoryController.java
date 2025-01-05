package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.StoryDto;
import com.kyu.pappy.model.pagenation.PageResponse;
import com.kyu.pappy.model.story.StoryPatchRequestBody;
import com.kyu.pappy.security.CustomUserDetails;
import com.kyu.pappy.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public PageResponse<StoryDto> getAllStory (
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return storyService.getStoryPaged(page, size);
    }

    @GetMapping("/{storyId}")
    public StoryDto getStoryById (@PathVariable("storyId") long storyId) {

        return storyService.getStoryById(storyId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStory(@RequestBody StoryDto createDto) {
        try {
            StoryDto savedStory = storyService.createStory(createDto);
            return ResponseEntity.ok(savedStory);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create product" + e.getMessage());
        }
    }

    @PatchMapping("/update/{storyId}")
    public ResponseEntity<StoryDto> updateStory(@PathVariable("storyId") Long storyId, @RequestBody StoryPatchRequestBody storyPatchRequestBody , Authentication authentication) {

        CustomUserDetails  currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = currentUserDetails.getUsername();
        var updateStory = storyService.updateStory(storyId , storyPatchRequestBody, username);
        return ResponseEntity.ok(updateStory);
    }

    @DeleteMapping("/delete/{storyId}")
    public void deleteStory(@PathVariable Long storyId, Authentication authentication) {

        CustomUserDetails  currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = currentUserDetails.getUsername();
        storyService.deleteStory(storyId, username);
    }
}
