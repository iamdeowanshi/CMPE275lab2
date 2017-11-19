package com.cmpe275.lab2.controller;

import com.cmpe275.lab2.model.Address;
import com.cmpe275.lab2.model.Player;
import com.cmpe275.lab2.model.Sponsor;
import com.cmpe275.lab2.service.PlayerService;
import com.cmpe275.lab2.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity createPlayer(@RequestParam(value = "firstname", required = true) String firstName,
									   @RequestParam(value = "lastname", required = true) String lastName,
									   @RequestParam(value = "description", required = false) String description,
									   @RequestParam(value = "email", required = true) String email,
									   @RequestParam(value = "street", required = false) String street,
									   @RequestParam(value = "city", required = false) String city,
									   @RequestParam(value = "state", required = false) String state,
									   @RequestParam(value = "zip", required = false) String zip,
									   @RequestParam(value = "sponsor", required = false) Long sponsorId) {

		try {

		    if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty())
                return HttpResponse.BAD_REQUEST.response("Invalid field values");

            Sponsor sponsor = null;
		    if (sponsorId != null)
			    sponsor = sponsorService.getSponsor(sponsorId);

            Player player = playerService.checkEmail(email);

            if( player != null)
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already Exist");

			player = new Player(firstName,lastName,email,description,street,city,state,zip);

			if (sponsorId != null)
				player.setSponsor(sponsor);

			playerService.addPlayer(player);

			return ResponseEntity.status(HttpStatus.CREATED).body(player);
		}catch(Exception e) {
			return HttpResponse.BAD_REQUEST.response();
		}
	}

	@PostMapping(value="/player/{id}")
	public ResponseEntity updatePlayer(@PathVariable("id") long PlayerId,
						@RequestParam(value="firstname")String firstName,
						@RequestParam(value = "lastname") String lastName,
						@RequestParam(value = "description", required = false) String description,
						@RequestParam(value = "email") String email,
						@RequestParam(value = "street", required = false) String street,
						@RequestParam(value = "city", required = false) String city,
						@RequestParam(value = "state", required = false) String state,
						@RequestParam(value = "zip", required = false) String zip,
						@RequestParam(value = "sponsor", required = false) Long sponsorId) {
		try {

            Sponsor sponsor = null;
            if (sponsorId != null)
                sponsor = sponsorService.getSponsor(sponsorId);

			Player player = playerService.getPlayer(PlayerId);

			player.setFirstName(firstName);
			player.setLastName(lastName);
			player.setEmail(email);

			player.setSponsor(sponsor);
			player.setCity(city);
			player.setStreet(street);
			player.setState(state);
			player.setZip(zip);
			player.setDescription(description);


			if (player != null)
				return ResponseEntity.ok(playerService.updatePlayer(player));

			return HttpResponse.NOT_FOUND.response();

		} catch(Exception e) {
			return HttpResponse.NOT_FOUND.response();
		}
	}

	@GetMapping(value="/player/{id}")
	public ResponseEntity getPlayerDetails(@PathVariable("id") long PlayerId) {
		try {
			Player player = playerService.getPlayer(PlayerId);
            player.setAddress(new Address(player.getStreet(),player.getCity(),player.getState(),player.getZip()));
            player.setOpponent(player.getOpponents());
			return ResponseEntity.ok(player);

		}catch(Exception e) {
			return HttpResponse.NOT_FOUND.response("Player Not Found");
		}
	}

	@DeleteMapping(value="/player/{id}")
	public ResponseEntity deletePlayer(@PathVariable("id") long PlayerId) {
		try {
			Player player = this.playerService.getPlayer(PlayerId);
			if(player.getSponsor()!=null) {
				player.getSponsor().getPlayers().remove(player.getSponsor().getPlayers().indexOf(player));
				sponsorService.updateSponsor(player.getSponsor());
			}
			for(Player player2:player.getOpponents()) {
				player2.getOpponents().remove(player2.getOpponents().indexOf(player));
				playerService.updatePlayer(player2);
			}

			player.setOpponents(null);
			playerService.updatePlayer(player);
			playerService.deletePlayer(PlayerId);

            player.setAddress(new Address(player.getStreet(),player.getCity(),player.getState(),player.getZip()));
            player.setOpponent(player.getOpponents());
			return ResponseEntity.ok(player);

		} catch (Exception e) {
			return HttpResponse.NOT_FOUND.response("Player Not found");
		}
	}

	@PutMapping(value="/opponents/{id1}/{id2}")
	public ResponseEntity insertOpponent(@PathVariable("id1") long PlayerId1, @PathVariable("id2") long PlayerId2) {
		try {
			Player player1 = playerService.getPlayer(PlayerId1);
			Player player2 = playerService.getPlayer(PlayerId2);

			if(player1.getOpponents()==null)
				player1.setOpponents(new ArrayList<>());

			if(player2.getOpponents()==null)
				player2.setOpponents(new ArrayList<>());

			for(Player opponent: player1.getOpponents()) {
				if (opponent.getId()==(player2.getId()))
					return ResponseEntity.ok("Already added opponent!");
			}

			player1.getOpponents().add(player2);
			player2.getOpponents().add(player1);

			playerService.updatePlayer(player1);
			playerService.updatePlayer(player2);

			return ResponseEntity.ok("Successfully added opponent!");

		}catch(Exception e) {
			return HttpResponse.NOT_FOUND.response();
		}
	}

	@DeleteMapping(value="/opponents/{id1}/{id2}")
	public ResponseEntity removeOpponent(@PathVariable("id1") long PlayerId1, @PathVariable("id2") long PlayerId2) {
		Player player1 = playerService.getPlayer(PlayerId1);
		Player player2 = playerService.getPlayer(PlayerId2);

		boolean isRemoved = false;

		try {
			int i=0;
			for(Player opponent: player1.getOpponents()) {
				if (opponent.getId()==(player2.getId())) {
					player1.getOpponents().remove(i);
					isRemoved = true;
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

			return isRemoved ?
					ResponseEntity.ok("Successfully Removed Opponent!") :
                    HttpResponse.NOT_FOUND.response();

		}catch(Exception e) {
			return HttpResponse.NOT_FOUND.response();
		}
	}
}
