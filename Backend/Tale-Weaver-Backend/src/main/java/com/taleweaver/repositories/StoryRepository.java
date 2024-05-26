package com.taleweaver.repositories;

import com.taleweaver.models.Story;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;


public interface StoryRepository extends JpaRepository<Story,Long> {

    @Query("select s from Story  s where s.email =:email")
    public Page<Story> findByEmail(String email, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value="delete from Story s where s.id = ?1")
    void deleteByIdCustom(Long Id);
}
