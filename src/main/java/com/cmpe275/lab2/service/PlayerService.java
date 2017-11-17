package com.cmpe275.lab2.service;


import com.cmpe275.lab2.model.Player;

public interface PlayerService {

	void addPlayer(Player player);
	 
    void deletePlayer(long playerId);
 
    Player getPlayer(long playerId);
 
    Player updatePlayer(Player player);
}
