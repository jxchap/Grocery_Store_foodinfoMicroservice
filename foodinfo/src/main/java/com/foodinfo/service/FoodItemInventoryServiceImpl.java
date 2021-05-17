package com.foodinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodinfo.domain.FoodItemInventory;
import com.foodinfo.repository.FoodItemInventoryRespository;

@Service
public class FoodItemInventoryServiceImpl implements FoodItemInventoryService {

	@Autowired
	FoodItemInventoryRespository foodItemInventoryRespository;

	@Override
	public FoodItemInventory findOldestFoodItemId(long foodItemId) {

		var foodItemList = foodItemInventoryRespository.findByFoodItemIdOrderByExpirationDateAsc(foodItemId);
		if (foodItemList.size() > 0) {
			return foodItemList.get(0);
		}

		return null;
	}

	@Override
	public FoodItemInventory save(FoodItemInventory foodItemInventory) {
		return foodItemInventoryRespository.save(foodItemInventory);
	}

	@Override
	public void delete(FoodItemInventory foodItemInventory) {
		foodItemInventoryRespository.delete(foodItemInventory);

	}

	@Override
	public List<FoodItemInventory> findAll() {
		return foodItemInventoryRespository.findAll();
	}

}
