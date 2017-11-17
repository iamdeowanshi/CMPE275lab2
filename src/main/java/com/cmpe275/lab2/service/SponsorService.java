package com.cmpe275.lab2.service;


import com.cmpe275.lab2.model.Sponsor;

public interface SponsorService {
	
	void addSponsor(Sponsor sponsor);
	 
    void deleteSponsor(long sponsorId);
 
    Sponsor getSponsor(long sponsorId);
 
    Sponsor updateSponsor(Sponsor sponsor);
}
