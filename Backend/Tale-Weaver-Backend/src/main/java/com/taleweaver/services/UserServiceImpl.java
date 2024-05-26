package com.taleweaver.services;

import com.taleweaver.jwt.JwtHelper;
import com.taleweaver.models.User;
import com.taleweaver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class UserServiceImpl implements UserService{
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> opt = userRepository.findById(userId);

        if(opt.isPresent()) return opt.get();
        else
            throw  new Exception("User not found with the id "+userId);
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = this.jwtHelper.getEmailFromJwtToken(jwt);
        if(email==null) throw new Exception("Token is not valid please provide a valid jwt token");

        User user = this.userRepository.findByEmail(email);

        if(user == null) throw new Exception("User not found with the email "+email);

        return user;

    }
}
