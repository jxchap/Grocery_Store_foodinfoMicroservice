package com.foodinfo.service;

import java.util.List;

import com.foodinfo.domain.InventoryReplenishment;

public interface InventoryReplenishmentService {
	
	InventoryReplenishment save(InventoryReplenishment inventoryReplenishment);
	
	List<InventoryReplenishment> findAll();
	
	InventoryReplenishment findById(long replenishmentId);

}
