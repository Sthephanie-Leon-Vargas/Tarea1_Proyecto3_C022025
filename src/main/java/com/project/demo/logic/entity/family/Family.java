package com.project.demo.logic.entity.family;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.home.Home;
import com.project.demo.logic.entity.person.Person;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "family")
@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<Person> people;

    @OneToOne(mappedBy = "family", cascade = CascadeType.ALL)
    private Home home;

    public Family() {}

    public Family(String name) {
        this.name = name;
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

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }
}