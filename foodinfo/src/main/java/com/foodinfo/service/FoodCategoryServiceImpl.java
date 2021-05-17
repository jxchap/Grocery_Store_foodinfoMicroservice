package com.foodinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodinfo.domain.FoodCategory;
import com.foodinfo.repository.FoodCategoryRepository;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

	@Autowired
	FoodCategoryRepository foodCategoryRepository;

	@Override
	public List<FoodCategory> getAllFoodCategories() {
		return foodCategoryRepository.findAll();
	}

	@Override
	public FoodCategory findCategoryById(long categoryId) {

		var foodCategoryResult = foodCategoryRepository.findById(categoryId);

		if (foodCategoryResult.isPresent()) {
			return foodCategoryResult.get();
		}
		return null;
	}

	@Override
	public FoodCategory save(FoodCategory foodCategory) {

		return foodCategoryRepository.save(foodCategory);
	}

	@Override
	public FoodCategory findByCategoryName(String categoryName) {
		return foodCategoryRepository.findByFoodCategoryName(categoryName);
	}

}
