package com.cmpe275.lab2.service;

import com.cmpe275.lab2.model.Player;
import com.cmpe275.lab2.repository.PlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

	@Autowired
    private PlayerDao playerDAO;
	
	@Override
	@Transactional
	public void addPlayer(Player player) {
		playerDAO.save(player);
	}

	@Override
	@Transactional
	public void deletePlayer(long playerId) {
		playerDAO.delete(playerId);
	}

	@Override
	@Transactional
	public Player getPlayer(long playerId) {
		return playerDAO.findOne(playerId);
	}

	@Override
	@Transactional
	public Player updatePlayer(Player player) {
		return playerDAO.save(player);
	}

	@Override
	public Player checkEmail(String email) {
		return (playerDAO.findByEmailAddress(email));
	}

}
