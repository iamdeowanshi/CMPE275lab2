package com.cmpe275.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sponsor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @JsonIgnore
    @Column(name = "street")
    private String street;
    @JsonIgnore
    @Column(name = "city")
    private String city;
    @JsonIgnore
    @Column(name = "state")
    private String state;
    @JsonIgnore
    @Column(name = "zip")
    private String zip;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sponsor")
    private List<Player> players;
    @Transient
    private Address address;

    public Sponsor() {
        super();
    }

    public Sponsor(String name, String description, String street, String city, String state, String zip) {
        super();
        this.name = name;
        this.description = description;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.street = street;
        this.address = new Address(street,city,state,zip);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}