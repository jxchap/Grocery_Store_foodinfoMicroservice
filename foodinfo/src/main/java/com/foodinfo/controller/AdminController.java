package com.foodinfo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.foodinfo.domain.FoodCategory;
import com.foodinfo.domain.FoodItem;
import com.foodinfo.domain.InventoryReplenishment;
import com.foodinfo.domain.Offer;
import com.foodinfo.service.FoodCategoryService;
import com.foodinfo.service.FoodItemService;
import com.foodinfo.service.InventoryReplenishmentService;
import com.foodinfo.service.OfferService;

@RestController
public class AdminController {

	@Autowired
	FoodItemService foodItemService;

	@Autowired
	FoodCategoryService foodCategoryService;

	@Autowired
	OfferService offerService;

	@Autowired
	InventoryReplenishmentService inventoryReplenishmentService;

	@PostMapping(value = "/searchFoodItemsByCategory")
	public ResponseEntity<List<FoodItem>> searchFoodItemsByCategory(@RequestBody JsonNode json) {

		String foodCategoryJson = json.get("categorySelection").asText();
		FoodCategory foodCategory = foodCategoryService.findByCategoryName(foodCategoryJson);
		var foodItemList = foodItemService.findByFoodCategory(foodCategory);

		return new ResponseEntity<>(foodItemList, HttpStatus.OK);

	}

	@PostMapping(value = "/getOffersForCategoryAdmin")
	public ResponseEntity<FoodCategory> getOffersForCategoryAdmin(@RequestBody JsonNode json) {

		String categoryName = json.get("categorySelection").asText();

		var categoryObject = foodCategoryService.findByCategoryName(categoryName);

		return new ResponseEntity<>(categoryObject, HttpStatus.OK);

	}

	@PostMapping(value = "/submitNewOffer")
	public ResponseEntity<JsonNode> submitNewOffer(@RequestBody JsonNode json) {
		ObjectMapper mapper = new ObjectMapper();

		Offer newOffer = mapper.convertValue(json.get("offerBody"), Offer.class);

		var categoryToUpdate = foodCategoryService.findByCategoryName(json.get("categorySelection").asText());

		
		var currentDate = LocalDate.now();
		var startDate = newOffer.getStartsOn();
		var endDate = newOffer.getExpiresOn();
		if ((startDate.equals(currentDate) || currentDate.isAfter(startDate))
				&& (endDate.equals(currentDate) || currentDate.isBefore(endDate))) {
			newOffer.setStatus("Active");
		} else {
			newOffer.setStatus("Inactive");
		}

		categoryToUpdate.getOffers().add(newOffer);

		ObjectNode objectNode = mapper.createObjectNode();
		if (foodCategoryService.save(categoryToUpdate) != null) {
			objectNode.put("message", "New Offer Submitted");
			var jsonResponse = mapper.convertValue(objectNode, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} else {
			objectNode.put("message", "Offer Not Submitted - Error");
			var jsonResponse = mapper.convertValue(objectNode, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/deleteOffer")
	public ResponseEntity<JsonNode> deleteOffer(@RequestBody JsonNode json) {

		long offerToDelete = json.get("offerIdToDelete").asLong();
		String categoryFromJson = json.get("categorySelection").asText();

		Offer offer = offerService.findOfferById(offerToDelete);

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		if (offer != null) {
			FoodCategory categoryToUpdate = foodCategoryService.findByCategoryName(categoryFromJson);
			categoryToUpdate.getOffers().remove(offer);
			foodCategoryService.save(categoryToUpdate);
			offerService.deleteById(offerToDelete);

			objectNode.put("message", "Offer Deleted");
			var jsonResponse = mapper.convertValue(objectNode, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} else {
			objectNode.put("message", "Offer Not Deleted - Error");
			var jsonResponse = mapper.convertValue(objectNode, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}

	}

	@PostMapping(value = "/submitModifiedProduct")
	public ResponseEntity<JsonNode> modifyOrAddProduct(@RequestBody JsonNode json) {

		FoodItem foodItemToUpdate = foodItemService.findById(json.get("productId").asLong());
		foodItemToUpdate.setFoodItemName(json.get("foodItemName").asText());
		foodItemToUpdate.setDescription(json.get("description").asText());
		foodItemToUpdate.setCost(json.get("cost").asDouble());
		foodItemToUpdate.setShelfLife(json.get("shelfLife").asInt());
		foodItemToUpdate.setBatchSize(json.get("batchSize").asInt());
		foodItemToUpdate.setStatus(json.get("status").asText());
		foodItemToUpdate.setReorderPoint(json.get("reorderPoint").asInt());

		FoodCategory foodCategoryForUpdate = foodCategoryService.findByCategoryName(json.get("foodCategory").asText());
		foodItemToUpdate.setFoodCategory(foodCategoryForUpdate);

		var saveResult = foodItemService.save(foodItemToUpdate);

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		if (saveResult != null) {
			objectNode.put("message", "Item Updated");
			var jsonResponse = mapper.convertValue(objectNode, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} else {
			objectNode.put("message", "Item Not Updated - Error");
			var jsonResponse = mapper.convertValue(objectNode, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}

	}

	@PostMapping(value = "/submitNewProduct")
	public ResponseEntity<JsonNode> submitNewProduct(@RequestBody JsonNode json) {

		FoodItem foodItemToAdd = new FoodItem();
		foodItemToAdd.setFoodItemName(json.get("foodItemName").asText());
		foodItemToAdd.setDescription(json.get("description").asText());
		foodItemToAdd.setCost(json.get("cost").asDouble());
		foodItemToAdd.setShelfLife(json.get("shelfLife").asInt());
		foodItemToAdd.setBatchSize(json.get("batchSize").asInt());
		foodItemToAdd.setStatus(json.get("status").asText());
		foodItemToAdd.setReorderPoint(json.get("reorderPoint").asInt());

		FoodCategory foodCategoryForAddingNewProduct = foodCategoryService
				.findByCategoryName(json.get("foodCategory").asText());
		foodItemToAdd.setFoodCategory(foodCategoryForAddingNewProduct);

		var saveResult = foodItemService.save(foodItemToAdd);
		var tempLocation = saveResult.getFoodItemId();
		saveResult.setImageLocation("" + tempLocation);
		saveResult = foodItemService.save(foodItemToAdd);
		
		System.out.println(saveResult.getFoodItemId());
		System.out.println(saveResult.getImageLocation());

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		if (saveResult != null) {

			var jsonResponse = mapper.convertValue(saveResult, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} else {
			objectNode.put("message", "Item Not Added - Error");
			var jsonResponse = mapper.convertValue(objectNode, JsonNode.class);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/getPurchasesAdmin")
	public ResponseEntity<List<InventoryReplenishment>> getPurchasesAdmin() {

		var responseList = inventoryReplenishmentService.findAll();

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@PostMapping(value = "/confirmPurchaseAdmin")
	public ResponseEntity<JsonNode> confirmPurchaseAdmin(@RequestBody JsonNode json) {

		var purchaseToUpdate = inventoryReplenishmentService.findById(json.get("purchaseToConfirm").asLong());

		if (purchaseToUpdate == null) {
			return new ResponseEntity<>(createJsonNodeWithMessage("Unable to Update"), HttpStatus.OK);
		}

		purchaseToUpdate.setPaid(true);
		inventoryReplenishmentService.save(purchaseToUpdate);

		return new ResponseEntity<>(createJsonNodeWithMessage("Purchase Updated"), HttpStatus.OK);

	}

	@PostMapping(value = "/doSomething")
	public ResponseEntity<JsonNode> doSomething(@RequestBody JsonNode json) {

		return null;

	}

	// utility method
	private JsonNode createJsonNodeWithMessage(String message) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("message", message);
		var response = mapper.convertValue(objectNode, JsonNode.class);
		return response;

	}

}
