package com.foodinfo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodinfo.domain.FoodCategory;
import com.foodinfo.service.FoodCategoryService;

@RestController
public class UniversalController {

	@Autowired
	FoodCategoryService foodCategoryService;

	@GetMapping(value = "/getFoodCategories")
	public ResponseEntity<Set<String>> getFoodCategories() {

		List<FoodCategory> returnFoodCategories = foodCategoryService.getAllFoodCategories();

		Set<String> setCategories = new HashSet<>();

		for (FoodCategory foodCategory : returnFoodCategories) {
			setCategories.add(foodCategory.getFoodCategoryName());
		}

		return new ResponseEntity<>(setCategories, HttpStatus.OK);

	}
}
