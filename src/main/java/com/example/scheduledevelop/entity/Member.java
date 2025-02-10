package com.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String memberName;

    @Column(nullable = false, unique = true)
    @Setter
    private String email;

    @Column(nullable = false)
    private String pwd;

    public Member() {
    }

    public Member(String memberName, String email, String pwd) {
        this.memberName = memberName;
        this.email = email;
        this.pwd = pwd;
    }
}
