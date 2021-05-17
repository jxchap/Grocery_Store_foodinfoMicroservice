package com.foodinfo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fooditem")
public class FoodItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long foodItemId;

	private String foodItemName;
	private String description;
	private double cost;
	private String imageLocation;
	private int shelfLife;
	private int batchSize;
	private int reorderPoint;
	private String status;
 
	@ManyToOne
	private FoodCategory foodCategory;
	
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public long getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(long foodItemId) {
		this.foodItemId = foodItemId;
	}

	public String getFoodItemName() {
		return foodItemName;
	}

	public void setFoodItemName(String foodItemName) {
		this.foodItemName = foodItemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public FoodCategory getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(FoodCategory foodCategory) {
		this.foodCategory = foodCategory;
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public FoodItem(long foodItemId, String foodItemName, String description, double cost, String imageLocation,
			int shelfLife, int batchSize, FoodCategory foodCategory) {
		super();
		this.foodItemId = foodItemId;
		this.foodItemName = foodItemName;
		this.description = description;
		this.cost = cost;
		this.imageLocation = imageLocation;
		this.shelfLife = shelfLife;
		this.batchSize = batchSize;
		this.foodCategory = foodCategory;
	}

	public FoodItem() {
		super();
	}

	public int getReorderPoint() {
		return reorderPoint;
	}

	public void setReorderPoint(int reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	
	

	
	
}
