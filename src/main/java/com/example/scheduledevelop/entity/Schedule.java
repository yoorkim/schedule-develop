package com.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    @Setter
    private String title;

    @Setter
    private String contents;

    public Schedule() {
    }

    public Schedule(String memberName, String title, String contents) {
        this.memberName = memberName;
        this.title = title;
        this.contents = contents;
    }

}
