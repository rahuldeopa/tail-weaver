package com.taleweaver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stories")
@Getter
@Setter
@NoArgsConstructor
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    private  String email ;
    private String title ;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String content ;
}
