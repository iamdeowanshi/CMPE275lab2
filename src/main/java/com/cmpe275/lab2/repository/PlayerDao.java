package com.cmpe275.lab2.repository;

import com.cmpe275.lab2.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerDao extends JpaRepository<Player, Long> {
}
