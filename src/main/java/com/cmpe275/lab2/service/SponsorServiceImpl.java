package com.cmpe275.lab2.service;

import com.cmpe275.lab2.model.Sponsor;
import com.cmpe275.lab2.repository.SponsorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SponsorServiceImpl implements SponsorService {
	
	@Autowired
    private SponsorDAO sponsorDAO;

	@Override
	@Transactional
	public void addSponsor(Sponsor sponsor) {
		sponsorDAO.save(sponsor);
	}

	@Override
	@Transactional
	public void deleteSponsor(long sponsorId) {
		sponsorDAO.delete(sponsorId);
	}

	@Override
	@Transactional
	public Sponsor getSponsor(long sponsorId) {
		return sponsorDAO.findOne(sponsorId);
	}

	@Override
	@Transactional
	public Sponsor updateSponsor(Sponsor sponsor) {
		return sponsorDAO.save(sponsor);
	}
}
