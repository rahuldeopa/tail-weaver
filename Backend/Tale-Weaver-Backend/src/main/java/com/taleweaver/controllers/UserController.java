package com.taleweaver.controllers;

import com.taleweaver.jwt.JwtHelper;
import com.taleweaver.models.User;
import com.taleweaver.repositories.UserRepository;
import com.taleweaver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
@Autowired
    private UserService userService;
@Autowired
    private UserRepository userRepository;

@Autowired
    private JwtHelper jwtHelper;

@GetMapping("/user_profile")
public ResponseEntity<Map<String , User>> getUser(@RequestHeader("Authorization")String jwt) throws Exception
{

   User user =this.userService.findUserByJwt(jwt);
   Map<String , User> map = new HashMap<>();
   map.put("user",user);
    System.out.println("User found "+user.getEmail()+" "+user.getFullname());

   return ResponseEntity.ok().body(map);
}
}
