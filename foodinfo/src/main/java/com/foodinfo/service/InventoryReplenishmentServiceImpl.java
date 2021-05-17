package com.foodinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodinfo.domain.InventoryReplenishment;
import com.foodinfo.repository.InventoryReplenishmentRepository;

@Service
public class InventoryReplenishmentServiceImpl implements InventoryReplenishmentService {

	@Autowired
	InventoryReplenishmentRepository inventoryReplenishmentRepository;

	@Override
	public InventoryReplenishment save(InventoryReplenishment inventoryReplenishment) {
		return inventoryReplenishmentRepository.save(inventoryReplenishment);
	}

	@Override
	public List<InventoryReplenishment> findAll() {
		return inventoryReplenishmentRepository.findAll();
	}

	@Override
	public InventoryReplenishment findById(long replenishmentId) {
		var result =  inventoryReplenishmentRepository.findById(replenishmentId);
		
		if(result.isPresent()) {
			return result.get();
		}
		
		return null;
	}

}
