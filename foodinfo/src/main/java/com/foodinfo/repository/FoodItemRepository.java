package com.foodinfo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodinfo.domain.FoodCategory;
import com.foodinfo.domain.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

	List<FoodItem> findAll();

	Page<FoodItem> findByFoodItemNameContainsAndDescriptionContains(String searchTerms, String searchTerms2, Pageable pageable);

	Page<FoodItem> findByFoodItemNameContainsAndDescriptionContainsAndFoodCategory(String searchTerms,
			String searchTerms2, FoodCategory foodCategory, Pageable pageable);
	
	List<FoodItem> findByFoodCategory(FoodCategory foodCategory);

}
