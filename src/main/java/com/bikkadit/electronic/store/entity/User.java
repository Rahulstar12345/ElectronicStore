package com.bikkadit.electronic.store.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password" )
    private String password;

    private String gender;

    @Column(length = 1001)
    private String about;

    @Column(name = "user_image_name")
    private String imageName;
}
