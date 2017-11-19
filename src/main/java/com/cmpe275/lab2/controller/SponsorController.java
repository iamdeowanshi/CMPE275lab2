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

@Controller
public class SponsorController {

	@Autowired
	private SponsorService sponsorService;
	@Autowired
    private PlayerService playerService;

	@PostMapping(value = "/sponsor")
	public ResponseEntity insertSponsor(@RequestParam(value = "name") String name,
									@RequestParam(value = "description", required = false) String description,
									@RequestParam(value = "street", required = false) String street,
									@RequestParam(value = "city", required = false) String city,
									@RequestParam(value = "state", required = false) String state,
									@RequestParam(value = "zip", required = false) String zip) {
		try {
			Sponsor sponsor = new Sponsor(name, description, street, city, state, zip);
			sponsorService.addSponsor(sponsor);

			return ResponseEntity.status(HttpStatus.CREATED).body(sponsor);

		}catch(Exception e) {
			return HttpResponse.BAD_REQUEST.response();
		}
	}

	@PatchMapping(value = "/sponsor/{id}")
	public ResponseEntity updateSponsor(@PathVariable("id") long SponsorId,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "street", required = false) String street,
                          @RequestParam(value = "city", required = false) String city,
                          @RequestParam(value = "state", required = false) String state,
                          @RequestParam(value = "zip", required = false) String zip) {

        try {
            Sponsor sponsor = sponsorService.getSponsor(SponsorId);

            sponsor.setName(name);
            sponsor.setDescription(description);
            sponsor.setStreet(street);
            sponsor.setCity(city);
            sponsor.setState(state);
            sponsor.setZip(zip);

            return  (sponsor != null) ?
                    ResponseEntity.ok(sponsorService.updateSponsor(sponsor)) :
                    HttpResponse.NOT_FOUND.response();

        }catch(Exception e) {
            return HttpResponse.NOT_FOUND.response();
        }
	}

	@GetMapping(value = "/sponsor/{id}")
	public ResponseEntity getSponsorDetails(@PathVariable("id") long SponsorId) {
        try {
            Sponsor sponsor = sponsorService.getSponsor(SponsorId);

            sponsor.setAddress(new Address(sponsor.getStreet(),sponsor.getCity(),sponsor.getState(),sponsor.getZip()));

            return  (sponsor == null) ?
                    HttpResponse.NOT_FOUND.response():
                    ResponseEntity.status(HttpStatus.OK).body(sponsor);
        }catch(Exception e) {
            return HttpResponse.NOT_FOUND.response();
        }
	}

	@DeleteMapping(value = "/sponsor/{id}")
	public ResponseEntity deleteSponsor(@PathVariable("id") long SponsorId) {
        try {
            Sponsor sponsor = sponsorService.getSponsor(SponsorId);

            for(Player player:sponsor.getPlayers()) {
                player.setSponsor(null);
                playerService.updatePlayer(player);
            }
            sponsorService.deleteSponsor(SponsorId);

            sponsor.setAddress(new Address(sponsor.getStreet(),sponsor.getCity(),sponsor.getState(),sponsor.getZip()));
            return ResponseEntity.ok(sponsor);
        }catch(Exception e) {
            return HttpResponse.NOT_FOUND.response();
        }
	}
}
