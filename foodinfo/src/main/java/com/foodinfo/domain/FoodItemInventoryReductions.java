package com.foodinfo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inventory_reductions")
public class FoodItemInventoryReductions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long inventoryReductionId;
	private long foodItemId;
	private long amount;

	public long getInventoryReductionId() {
		return inventoryReductionId;
	}

	public void setInventoryReductionId(long inventoryReductionId) {
		this.inventoryReductionId = inventoryReductionId;
	}

	public long getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(long foodItemId) {
		this.foodItemId = foodItemId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public FoodItemInventoryReductions(long foodItemId, long amount) {
		super();
		this.foodItemId = foodItemId;
		this.amount = amount;
	}

	public FoodItemInventoryReductions() {
	}

}
