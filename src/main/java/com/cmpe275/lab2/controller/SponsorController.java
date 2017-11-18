package com.cmpe275.lab2.controller;

import com.cmpe275.lab2.model.Sponsor;
import com.cmpe275.lab2.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SponsorController {

	@Autowired
	private SponsorService sponsorService;

	@PostMapping(value = "/sponsor")
	public @ResponseBody
	Sponsor newPlayer(@RequestParam(value = "name", required = true) String name,
					  @RequestParam(value = "description", required = false) String description,
					  @RequestParam(value = "street", required = false) String street,
					  @RequestParam(value = "city", required = false) String city,
					  @RequestParam(value = "state", required = false) String state,
					  @RequestParam(value = "zip", required = false) String zip) {
		Sponsor sponsor = new Sponsor(name, description, street, city, state, zip);
		sponsorService.addSponsor(sponsor);

		return sponsor;
	}

	@PatchMapping(value = "/sponsor/{id}")
	public @ResponseBody
    Sponsor updateSponsor(@PathVariable("id") long SponsorId,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "street", required = false) String street,
                          @RequestParam(value = "city", required = false) String city,
                          @RequestParam(value = "state", required = false) String state,
                          @RequestParam(value = "zip", required = false) String zip) {

		Sponsor sponsor = sponsorService.getSponsor(SponsorId);

		if (sponsor != null) {
			if (name != null) {
				sponsor.setName(name);
			}
			if (description != null) {
				sponsor.setDescription(description);
			}
			if (street != null) {
				sponsor.setStreet(street);
			}
			if (city != null) {
				sponsor.setCity(city);

			}if (state != null) {
				sponsor.setState(state);

			}if (zip != null) {
				sponsor.setZip(zip);

			}

			return sponsorService.updateSponsor(sponsor);
		}

		return null;
	}

	@GetMapping(value = "/sponsor/{id}")
	public @ResponseBody
    Sponsor getSponsorDetails(@PathVariable("id") long SponsorId) {

		Sponsor sponsor = sponsorService.getSponsor(SponsorId);

		return sponsor;
	}

	@DeleteMapping(value = "/sponsor/{id}")
	public @ResponseBody
    String deleteSponsor(@PathVariable("id") long SponsorId) {

	    sponsorService.deleteSponsor(SponsorId);

		return "Deleted Successfully!";
	}
}
