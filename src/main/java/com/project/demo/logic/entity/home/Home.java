package com.project.demo.logic.entity.home;

import com.project.demo.logic.entity.family.Family;
import jakarta.persistence.*;

@Table(name = "home")
@Entity
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @OneToOne
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    private Family family;

    public Home() {}

    public Home(String name, String address, Family family) {
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
}