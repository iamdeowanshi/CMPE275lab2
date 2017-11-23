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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "player_sponsor")
    private Sponsor sponsor;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "player_opponent")
    private List<Player> opponents;
    @Embedded
    private Address address;
    @Transient
    private String opponent;

    public Player() {
        super();
    }

    public Player(String firstName, String lastName, String email, String description, String street, String city, String state, String zip) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.address = new Address(street,city,state,zip);
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setOpponent(List<Player> opponents) {
        String opp = "[";
        for (Player opponent : opponents) {
            opp += opponent.getId() + ",";
        }
        if (opponents.size() > 0)
            opp = opp.substring(0, opp.length() - 1);
        opp += "]";

        this.opponent = opp;
    }

    public String getOpponent() {
        return opponent;
    }
}

