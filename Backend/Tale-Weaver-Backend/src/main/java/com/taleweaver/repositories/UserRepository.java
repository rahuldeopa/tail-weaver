package com.taleweaver.repositories;

import com.taleweaver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {

    public User findByEmail(String email);
    public User findById(long id);

}
