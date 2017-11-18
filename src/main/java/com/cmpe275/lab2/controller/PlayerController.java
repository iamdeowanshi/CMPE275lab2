package com.cmpe275.lab2.controller;

import com.cmpe275.lab2.model.Player;
import com.cmpe275.lab2.model.Sponsor;
import com.cmpe275.lab2.service.PlayerService;
import com.cmpe275.lab2.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PlayerController {

	@Autowired
	private PlayerService playerService;
	@Autowired
    private SponsorService sponsorService;
	
	@GetMapping(value = "/")
	public @ResponseBody
	String home() {
		return "Hello CMPE 275 LAB2";
	}

	@PostMapping(value = "/player")
	public @ResponseBody
	Player createPlayer(@RequestParam(value = "firstname", required = true) String firstName,
						@RequestParam(value = "lastname", required = true) String lastName,
						@RequestParam(value = "description", required = false) String description,
						@RequestParam(value = "email", required = true) String email,
						@RequestParam(value = "street", required = false) String street,
						@RequestParam(value = "city", required = false) String city,
						@RequestParam(value = "state", required = false) String state,
						@RequestParam(value = "zip", required = false) String zip,
                        @RequestParam(value = "sponsor_id", required = false) Long sponsorId) {

		Player player = new Player(firstName,lastName,email,description,street,city,state,zip);

		if (sponsorId != null) {
            Sponsor sponsor = sponsorService.getSponsor(sponsorId);

            player.setSponsor(sponsor);
        }
		playerService.addPlayer(player);

		return player;
	}

	@PatchMapping(value="/player/{id}")
	public @ResponseBody
	Player updatePlayer(@PathVariable("id") long PlayerId,
						@RequestParam(value="firstname",required=false)String firstName,
						@RequestParam(value = "lastname", required = false) String lastName,
						@RequestParam(value = "description", required = false) String description,
						@RequestParam(value = "email", required = false) String email,
						@RequestParam(value = "street", required = false) String street,
						@RequestParam(value = "city", required = false) String city,
						@RequestParam(value = "state", required = false) String state,
						@RequestParam(value = "zip", required = false) String zip,
                        @RequestParam(value = "sponsor_id") Long sponsorId) {

		Player player = this.playerService.getPlayer(PlayerId);

		if (player != null) {
			if (firstName != null) {
				player.setFirstName(firstName);
			}
			if (lastName != null) {
				player.setLastName(lastName);
			}
			if (description != null) {
				player.setDescription(description);
			}
			if (street != null) {
				player.setStreet(street);
			}
			if (city != null) {
				player.setCity(city);

			}if (state != null) {
				player.setState(state);

			}if (zip != null) {
				player.setZip(zip);

			}if (sponsorId != null) {
                Sponsor sponsor = sponsorService.getSponsor(sponsorId);

                player.setSponsor(sponsor);
            }

			return playerService.updatePlayer(player);
		}

		return null;
	}

	@GetMapping(value="/player/{id}")
	public @ResponseBody
	String getPlayerDetails(@PathVariable("id") long PlayerId) {

		return playerService.getPlayer(PlayerId).toString();
	}

	@DeleteMapping(value="/player/{id}")
	public @ResponseBody
	String deletePlayer(@PathVariable("id") long PlayerId) {

		playerService.deletePlayer(PlayerId);

		return "Deleted Successfully!";
	}
	@GetMapping(value="/opponents/{id1}/{id2}")
	public @ResponseBody
	String insertOpponent(@PathVariable("id1") long PlayerId1, @PathVariable("id2") long PlayerId2) {
		Player player1 = playerService.getPlayer(PlayerId1);
		Player player2 = playerService.getPlayer(PlayerId2);

		if(player1.getOpponents()==null)
			player1.setOpponents(new ArrayList<>());

		if(player2.getOpponents()==null)
			player2.setOpponents(new ArrayList<>());

		player1.getOpponents().add(player2);
		player2.getOpponents().add(player1);

		playerService.updatePlayer(player1);
		playerService.updatePlayer(player2);

		return "Successfully added opponent";
	}

	@DeleteMapping(value="/opponents/{id1}/{id2}")
	public @ResponseBody
	String removeOponent(@PathVariable("id1") long PlayerId1, @PathVariable("id2") long PlayerId2) {
		Player player1 = playerService.getPlayer(PlayerId1);
		Player player2 = playerService.getPlayer(PlayerId2);
		boolean flag = false;

		int i=0;
		for(Player opponent: player1.getOpponents()) {
			if (opponent.getId()==(player2.getId())) {
				player1.getOpponents().remove(i);
				flag = true;
				break;
			}
			++i;
		}

		i=0;
		for(Player opponent: player2.getOpponents()) {
			if (opponent.getId()==(player1.getId())) {
				player2.getOpponents().remove(i);
				break;
			}
			++i;
		}

		playerService.updatePlayer(player1);
		playerService.updatePlayer(player2);

		return flag ? "Removed Opponent Successfully" : "Players are not Opponents";
	}
}
