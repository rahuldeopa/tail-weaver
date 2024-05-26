package com.taleweaver.services;

import com.taleweaver.jwt.JwtResponse;
import com.taleweaver.models.Story;

public interface StoryService {

    public JwtResponse generateStory(String prompt, String jwt);
    public JwtResponse saveStory(Story story ,String jwt);
    public JwtResponse deleteStory(long storyId , String jwt);

    public  Story getStory(long id);


}
