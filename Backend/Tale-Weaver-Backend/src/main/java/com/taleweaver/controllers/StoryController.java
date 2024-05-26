package com.taleweaver.controllers;

import com.taleweaver.jwt.JwtResponse;
import com.taleweaver.models.Story;

import com.taleweaver.repositories.StoryRepository;
import com.taleweaver.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
     private  StoryService storyService;

    @Autowired
    private StoryRepository storyRepository;

    @GetMapping("/generate/{prompt}")
    public ResponseEntity<JwtResponse> generateStory(@PathVariable String prompt ,@RequestHeader("Authorization") String jwt)
    {
        return ResponseEntity.ok().body(storyService.generateStory(prompt,jwt));
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<Map<String , Story>> getStory(@PathVariable("storyId")long id, @RequestHeader("Autorization")String jwt)
    {
        Map<String , Story> map = new HashMap<>();

        Story story = this.storyService.getStory(id);
        map.put("story",story);
        return ResponseEntity.ok().body(map);

    }
    @PostMapping()
    public ResponseEntity<JwtResponse> addStory(@RequestBody Story story, Principal principal, @RequestHeader("Authorization") String jwt)
    {
       // System.out.println(principal.getName());
        story.setEmail(principal.getName());
        return ResponseEntity.ok().body(storyService.saveStory(story,jwt));

    }
    @DeleteMapping("/{storyId}")
    public ResponseEntity<JwtResponse> deleteStory(@PathVariable("storyId")long id, @RequestHeader("Authorization") String jwt)
    {JwtResponse jwtResponse = new JwtResponse();
        jwtResponse = this.storyService.deleteStory(id,jwt);
        return ResponseEntity.ok().body(jwtResponse);

    }
    @PutMapping()
    public ResponseEntity<JwtResponse>  updateStory(@RequestBody Story story,@RequestHeader("Authorization") String jwt)
    {
        return ResponseEntity.ok().body(storyService.saveStory(story,jwt));

    }
    @GetMapping("/all/{page}")
    public Map<String , Object> getAllStories(Principal principal,@PathVariable("page") Integer page)
    {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Story> storyPage = this.storyRepository.findByEmail(principal.getName(), pageable);
        int total = storyPage.getTotalPages();
        List<Story> stories = storyPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("stories", stories);
        response.put("totalPages", total);
        return response;
    }

}
