package com.foodinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodinfo.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long>{
	
	List<FoodCategory> findAll();
	
	FoodCategory findByFoodCategoryName(String foodCategoryName);

}
