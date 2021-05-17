package com.foodinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodinfo.domain.FoodItemInventoryReductions;
import com.foodinfo.repository.FoodItemInventoryReductionsRepository;

@Service
public class FoodItemInventoryReductionServiceImpl implements FoodItemInventoryReductionService {

	@Autowired
	FoodItemInventoryReductionsRepository foodItemInventoryReductionsRepository;

	@Override
	public FoodItemInventoryReductions save(FoodItemInventoryReductions foodItemInventoryReductions) {
		return foodItemInventoryReductionsRepository.save(foodItemInventoryReductions);
	}

	@Override
	public List<FoodItemInventoryReductions> findAll() {
		return foodItemInventoryReductionsRepository.findAll();
	}

	@Override
	public void delete(FoodItemInventoryReductions foodItemInventoryReductions) {
		foodItemInventoryReductionsRepository.delete(foodItemInventoryReductions);
	}

}
