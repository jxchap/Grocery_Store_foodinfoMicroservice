package com.foodinfo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.foodinfo.domain.FoodCategory;
import com.foodinfo.domain.FoodItem;
import com.foodinfo.domain.FoodItemPage;

public interface FoodItemService {

	List<FoodItem> findAll();
	
	Page<FoodItem> getFoodItemsUsingSearchTerms(String foodItemName, String description, String foodCategory, FoodItemPage foodItemPage);
	
	List<FoodItem> findMultipleById(List<Long> foodItemIds);
	
	List<FoodItem> findByFoodCategory(FoodCategory foodCategory);
	
	FoodItem save(FoodItem foodItem);
	
	FoodItem findById(long foodItemId);
	

}
