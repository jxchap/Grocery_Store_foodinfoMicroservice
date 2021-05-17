package com.foodinfo.service;

import java.util.List;

import com.foodinfo.domain.FoodItemInventory;

public interface FoodItemInventoryService {
	
	FoodItemInventory findOldestFoodItemId(long foodItemId);
	
	FoodItemInventory save(FoodItemInventory foodItemInventory);
	
	void delete(FoodItemInventory foodItemInventory);
	
	List<FoodItemInventory> findAll();

}
