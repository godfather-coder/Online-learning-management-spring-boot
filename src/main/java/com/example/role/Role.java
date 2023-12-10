package com.example.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @SequenceGenerator(name="user_sequence",sequenceName = "user_sequence",allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    public Role(String name){
        this.name=name;
    }

}
