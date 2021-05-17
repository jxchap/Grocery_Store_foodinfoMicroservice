package com.foodinfo.service;

import java.util.List;

import com.foodinfo.domain.FoodItemInventoryReductions;

public interface FoodItemInventoryReductionService {
	
	FoodItemInventoryReductions save(FoodItemInventoryReductions foodItemInventoryReductions);
	List<FoodItemInventoryReductions> findAll();

	
	void delete(FoodItemInventoryReductions foodItemInventoryReductions);
}
