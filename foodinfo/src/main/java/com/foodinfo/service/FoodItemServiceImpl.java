package com.foodinfo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.foodinfo.domain.FoodCategory;
import com.foodinfo.domain.FoodItem;
import com.foodinfo.domain.FoodItemPage;
import com.foodinfo.repository.FoodCategoryRepository;
import com.foodinfo.repository.FoodItemRepository;

@Service
public class FoodItemServiceImpl implements FoodItemService {

	@Autowired
	FoodItemRepository foodItemRepository;

	@Autowired
	FoodCategoryRepository foodCategoryRepository;

	@Override
	public List<FoodItem> findAll() {
		return foodItemRepository.findAll();
	}

	@Override
	public Page<FoodItem> getFoodItemsUsingSearchTerms(String foodItemName, String description, String foodCategory,
			FoodItemPage foodItemPage) {

		Sort sort = Sort.by(foodItemPage.getSortDirection(), foodItemPage.getSortBy());
		Pageable pageable = PageRequest.of(foodItemPage.getPageNumber(), foodItemPage.getPageSize(), sort);

		if (foodItemName.isBlank())
			foodItemName = "";
		if (description.isBlank())
			description = "";

		foodItemName = foodItemName.trim();
		description = description.trim();

		if (foodCategory.equalsIgnoreCase("all")) {
			foodCategory = "";
			return foodItemRepository.findByFoodItemNameContainsAndDescriptionContains(foodItemName, description,
					pageable);
		} else {
			FoodCategory foodCategoryObject = foodCategoryRepository.findByFoodCategoryName(foodCategory);
			return foodItemRepository.findByFoodItemNameContainsAndDescriptionContainsAndFoodCategory(foodItemName,
					description, foodCategoryObject, pageable);
		}
	}

	@Override
	public List<FoodItem> findMultipleById(List<Long> foodItemIds) {

		List<FoodItem> returnList = new ArrayList<>();

		for (Long long1 : foodItemIds) {

			var tempItem = foodItemRepository.findById(long1);

			if (tempItem.isPresent()) {
				returnList.add(tempItem.get());
			}

		}

		return returnList;
	}

	@Override
	public List<FoodItem> findByFoodCategory(FoodCategory foodCategory) {
		return foodItemRepository.findByFoodCategory(foodCategory);

	}

	@Override
	public FoodItem save(FoodItem foodItem) {
		return foodItemRepository.save(foodItem);
	}

	@Override
	public FoodItem findById(long foodItemId) {

		var foodItem = foodItemRepository.findById(foodItemId);

		if (foodItem.isPresent()) {
			return foodItem.get();
		}

		return null;
	}
}
