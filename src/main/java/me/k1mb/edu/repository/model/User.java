package me.k1mb.edu.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "username")
    String username;

    @Column(name = "email", unique = true)
    String email;
}
