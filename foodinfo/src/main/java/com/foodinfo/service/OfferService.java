package com.foodinfo.service;

import java.util.List;

import com.foodinfo.domain.Offer;

public interface OfferService {
	
	List<Offer> getAllOffers();
	
	Offer saveNewOffer(Offer offer);
	
	Offer findOfferById(long offerId);
	
	void deleteById(long offerId);
	
	List<Offer> findByStatus(String status);

}
