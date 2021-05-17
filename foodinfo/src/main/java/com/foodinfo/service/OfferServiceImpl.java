package com.foodinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodinfo.domain.Offer;
import com.foodinfo.repository.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	OfferRepository offerRepository;

	@Override
	public List<Offer> getAllOffers() {
		return offerRepository.findAll();
	}

	@Override
	public Offer saveNewOffer(Offer offer) {

		return offerRepository.save(offer);
	}

	@Override
	public Offer findOfferById(long offerId) {

		var offerResult = offerRepository.findById(offerId);

		if (offerResult.isPresent()) {
			return offerResult.get();
		}
		return null;
	}

	@Override
	public void deleteById(long offerId) {
		offerRepository.deleteById(offerId);

	}

	@Override
	public List<Offer> findByStatus(String status) {
		return offerRepository.findByStatus(status);
	}

}
