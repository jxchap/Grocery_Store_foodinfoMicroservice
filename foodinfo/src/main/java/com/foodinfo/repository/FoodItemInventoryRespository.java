package com.foodinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodinfo.domain.FoodItemInventory;

public interface FoodItemInventoryRespository extends JpaRepository<FoodItemInventory, Long>{
	
	List<FoodItemInventory> findByFoodItemIdOrderByExpirationDateAsc(long foodItemId);

}
