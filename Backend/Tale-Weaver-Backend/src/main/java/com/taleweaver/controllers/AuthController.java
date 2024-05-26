package com.taleweaver.controllers;

import com.taleweaver.config.CustomUserDetailsService;
import com.taleweaver.jwt.JwtHelper;
import com.taleweaver.jwt.JwtRequest;
import com.taleweaver.jwt.JwtResponse;
import com.taleweaver.models.User;
import com.taleweaver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtHelper jwtHelper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public JwtResponse createUser(@RequestBody User user) throws Exception{
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullname();

        //verify email

        User isExistEmail = userRepository.findByEmail(email);

        if(isExistEmail!=null)
        {
            // user already exist
            throw new Exception("User already exist with the provided email with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullname(fullName);

        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHelper.generateToken(authentication);

        JwtResponse res = new JwtResponse();

        res.setJwt(token);
        res.setMessage("signup success");
        return res;

    }

    @PostMapping("/signin")
    public JwtResponse signinHandler(@RequestBody JwtRequest loginRequest){

        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHelper.generateToken(authentication);

        JwtResponse res = new JwtResponse();

        res.setJwt(token);
        res.setMessage("signin success");
        return res;

    }

    private Authentication authenticate(String userName, String password) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        if(userDetails==null)
            throw new BadCredentialsException("Bad Credentials ! user not found");

        if(!passwordEncoder.matches(password,userDetails.getPassword()))
            throw  new BadCredentialsException("invalid Password");

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
