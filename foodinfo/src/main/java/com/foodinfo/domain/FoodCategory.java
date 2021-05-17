package com.foodinfo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="foodcategory")
public class FoodCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long foodCategoryId;
	
	private String foodCategoryName;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Offer> offers;

	public String getFoodCategoryName() {
		return foodCategoryName;
	}

	public void setFoodCategoryName(String foodCategoryName) {
		this.foodCategoryName = foodCategoryName;
	}

	public long getFoodCategoryId() {
		return foodCategoryId;
	}

	public void setFoodCategoryId(long foodCategoryId) {
		this.foodCategoryId = foodCategoryId;
	}

	public Set<Offer> getOffers() {
		return offers;
	} 

	public void setOffers(Set<Offer> offer) {
		this.offers = offer;
	}

	public FoodCategory(long foodCategoryId, String foodCategoryName, Set<Offer> offers) {
		super();
		this.foodCategoryId = foodCategoryId;
		this.foodCategoryName = foodCategoryName;
		this.offers = offers;
	}

	public FoodCategory() {
		super();
	}
	
	
	
}


