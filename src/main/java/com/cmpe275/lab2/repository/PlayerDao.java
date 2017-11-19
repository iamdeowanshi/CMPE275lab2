package com.cmpe275.lab2.repository;

import com.cmpe275.lab2.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerDao extends JpaRepository<Player, Long> {

    @Query("select p from Player p where p.email = ?1")
    Player findByEmailAddress(String emailAddress);
}
