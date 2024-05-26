package com.taleweaver.services;


import com.taleweaver.jwt.JwtResponse;
import com.taleweaver.models.Story;
import com.taleweaver.models.User;
import com.taleweaver.repositories.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class StoryServiceImpl implements  StoryService{



    @Autowired
    private StoryRepository storyRepository;

    @Autowired CallPythonService callPythonService;

    @Autowired
    private UserService userService;
    @Override
    public JwtResponse generateStory(String prompt ,  String jwt ) {

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt(jwt);

        if(prompt==null)
        {

            jwtResponse.setMessage("Prompt cannot be null");

        }

       else {
            try {
                String story = callPythonService.generateStory(prompt);
                jwtResponse.setMessage(story);
            } catch (IOException e) {

                jwtResponse.setMessage("Story cannot be generated due to some error "+e.getMessage());
            }
        }
    return jwtResponse;
    }

    @Override
    public JwtResponse saveStory(Story story,  String jwt) {
        JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setJwt(jwt);
        try {
            User user = this.userService.findUserByJwt(jwt);


             story.setEmail(user.getEmail());
             storyRepository.save(story);
                jwtResponse.setMessage("Story saved successfully");
        } catch (Exception e) {

            jwtResponse.setMessage("Story cannot be save due to error"+e.getMessage());
        }
        return jwtResponse;
    }

    @Override
    public JwtResponse deleteStory(long storyId, String jwt) {
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt(jwt);
        try {

            storyRepository.deleteByIdCustom(storyId);
            jwtResponse.setMessage("Story deleted successfully");
        } catch (Exception e) {

            jwtResponse.setMessage("Story cannot be deleted due to error"+e.getMessage());
        }
        return jwtResponse;
    }

    @Override
    public Story getStory(long id ) {


      Story story =  this.storyRepository.getById(id);

      return story;
    }

}
