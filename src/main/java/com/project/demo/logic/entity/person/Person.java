package com.project.demo.logic.entity.person;

import com.project.demo.logic.entity.family.Family;
import jakarta.persistence.*;

@Table(name = "person")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    public Person() {}

    public Person(String name, String lastname, Family family) {
        this.name = name;
        this.lastname = lastname;
        this.family = family;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
}