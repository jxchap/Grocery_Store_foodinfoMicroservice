package com.foodinfo.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fooditeminventory")
public class FoodItemInventory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long foodInventoryId;
	
	private long foodItemId;
	
	private LocalDate expirationDate;
	 
	private long inventoryAmount;
 
	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public long getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(long foodItemId) {
		this.foodItemId = foodItemId;
	}

	public long getInventoryAmount() {
		return inventoryAmount;
	}

	public void setInventoryAmount(long inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}

}
