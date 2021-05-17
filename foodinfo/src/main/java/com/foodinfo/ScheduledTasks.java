package com.foodinfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.foodinfo.domain.FoodItemInventory;
import com.foodinfo.domain.InventoryReplenishment;
import com.foodinfo.domain.Offer;
import com.foodinfo.service.FoodItemInventoryReductionService;
import com.foodinfo.service.FoodItemInventoryService;
import com.foodinfo.service.FoodItemService;
import com.foodinfo.service.InventoryReplenishmentService;
import com.foodinfo.service.OfferService;

@Component
public class ScheduledTasks {

	@Autowired
	FoodItemInventoryService foodItemInventoryService;

	@Autowired
	FoodItemInventoryReductionService foodItemInventoryReductionService;

	@Autowired
	FoodItemService foodItemService;

	@Autowired
	InventoryReplenishmentService inventoryReplenishmentService;

	@Autowired
	OfferService offerService;

	@EventListener(ApplicationReadyEvent.class)
	@Scheduled(cron = "0 */10 * ? * *")
	private void processReductionsTable() {

		applyReductionsToInventory();

		checkForInventoryBelowReorderPoint();

	}

	@EventListener(ApplicationReadyEvent.class)
	@Scheduled(cron = "0 0 12 * * ?")
	private void updateOfferStatus() {

		var currentDate = LocalDate.now();

		for (Offer offer : offerService.getAllOffers()) {
			var startDate = offer.getStartsOn();
			var endDate = offer.getExpiresOn();

			if ((startDate.equals(currentDate) || currentDate.isAfter(startDate))
					&& (endDate.equals(currentDate) || currentDate.isBefore(endDate))) {
				offer.setStatus("Active");
			} else {
				offer.setStatus("Inactive");
			}
			offerService.saveNewOffer(offer);
		}

	}

	private void applyReductionsToInventory() {

		var reductionList = foodItemInventoryReductionService.findAll();

		Map<Long, Long> reductionListReduced = new HashMap<>();
		for (var reduction : reductionList) {
			reductionListReduced.merge(reduction.getFoodItemId(), reduction.getAmount(), (a, b) -> a + b);
		}

		reductionListReduced.forEach((reductionFoodItemId, reductionAmount) -> {
			while (reductionAmount > 0) {

				var currentInventoryRecord = foodItemInventoryService.findOldestFoodItemId(reductionFoodItemId);

				if (currentInventoryRecord != null) {

					var inventoryAmount = currentInventoryRecord.getInventoryAmount();

					while (inventoryAmount > 0 && reductionAmount > 0) {
						reductionAmount--;
						currentInventoryRecord.setInventoryAmount(--inventoryAmount);
					}

					if (inventoryAmount < 1) {
						foodItemInventoryService.delete(currentInventoryRecord);
					} else {
						foodItemInventoryService.save(currentInventoryRecord);
					}
				} else {
					reorderFoodItemId(reductionFoodItemId);
				}

			}

		});
		// clear reductions from db
		reductionList.forEach((e) -> foodItemInventoryReductionService.delete(e));
	}

	private void checkForInventoryBelowReorderPoint() {
		var inventoryList = foodItemInventoryService.findAll();

		Map<Long, Long> inventoryListReduced = new HashMap<>();
		for (var inventoryItem : inventoryList) {
			inventoryListReduced.merge(inventoryItem.getFoodItemId(), inventoryItem.getInventoryAmount(),
					(a, b) -> a + b);
		}

		var foodItemList = foodItemService.findAll();

		foodItemList.forEach(foodItem -> {

			if (inventoryListReduced.get(foodItem.getFoodItemId()) == null) {
				reorderFoodItemId(foodItem.getFoodItemId());
			}

			if (inventoryListReduced.get(foodItem.getFoodItemId()) != null
					&& inventoryListReduced.get(foodItem.getFoodItemId()) < foodItem.getReorderPoint()) {
				reorderFoodItemId(foodItem.getFoodItemId());
			}

		});

	}

	private void reorderFoodItemId(long foodItemId) {
		var foodItemToRefill = foodItemService.findById(foodItemId);
		FoodItemInventory newInventory = new FoodItemInventory();
		newInventory.setExpirationDate(LocalDate.now().plusDays(foodItemToRefill.getShelfLife()));
		newInventory.setFoodItemId(foodItemToRefill.getFoodItemId());
		newInventory.setInventoryAmount(foodItemToRefill.getBatchSize());

		foodItemInventoryService.save(newInventory);

		InventoryReplenishment inventoryReplenishment = new InventoryReplenishment();
		inventoryReplenishment.setFoodItemId(foodItemToRefill.getFoodItemId());
		inventoryReplenishment.setFoodItemName(foodItemToRefill.getFoodItemName());
		inventoryReplenishment.setOrderDate(LocalDate.now());
		inventoryReplenishment.setQuantityOrdered(foodItemToRefill.getBatchSize());

		inventoryReplenishmentService.save(inventoryReplenishment);
	}

}
