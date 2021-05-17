package com.foodinfo.service;

import java.util.List;

import com.foodinfo.domain.FoodCategory;

public interface FoodCategoryService {
	List<FoodCategory> getAllFoodCategories();
	
	FoodCategory findCategoryById(long categoryId);
	
	FoodCategory save(FoodCategory foodCategory);
	
	FoodCategory findByCategoryName(String categoryName);
}
