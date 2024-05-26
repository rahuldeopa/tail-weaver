package com.taleweaver.services;

import com.taleweaver.models.User;

public interface UserService {
    public User findUserById(Long userId) throws  Exception;
    public User findUserByJwt(String jwt)throws Exception;
}
