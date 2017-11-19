package com.cmpe275.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;

    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private String zip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "player_sponsor")
    private Sponsor sponsor;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "player_opponent")
    private List<Player> opponents;

    public Player() {
        super();
    }

    public Player(String firstName, String lastName, String email, String description, String street, String city, String state, String zip) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.street = street;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    @JsonIgnore
    public List<Player> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Player> opponents) {
        this.opponents = opponents;
    }

    @Override
    public String toString() {
        String opp = "[";
        for (Player opponent : opponents) {
            opp += opponent.getId() + ",";
        }
        opp = opp.substring(0, opp.length() - 1);
        opp += "]";

        return "{id:" + id + ", firstName:" + firstName + ", lastName:" + lastName + ", email:" + email
                + ", description:" + description + ", street:" + street + ", city:" + city + ", state:" + state + ", zip:" + zip +
                ", sponsor:" + sponsor + ", opponents:"
                + opp + "}";
    }

}

