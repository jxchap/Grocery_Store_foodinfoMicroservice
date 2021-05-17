package com.foodinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodinfo.domain.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>{
	
	List<Offer> findByStatus(String status);

}
