package com.foodinfo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.foodinfo.domain.FoodItem;
import com.foodinfo.domain.FoodItemInventoryReductions;
import com.foodinfo.domain.FoodItemPage;
import com.foodinfo.domain.Offer;
import com.foodinfo.service.FoodCategoryService;
import com.foodinfo.service.FoodItemInventoryReductionService;
import com.foodinfo.service.FoodItemService;
import com.foodinfo.service.OfferService;

@RestController
public class CustomerController {

	@Autowired
	FoodItemService foodItemService;

	@Autowired
	FoodCategoryService foodCategoryService;

	@Autowired
	OfferService offerService;

	@Autowired
	FoodItemInventoryReductionService foodItemInventoryReductionService;

	@PostMapping(value = "/searchFoodItems")
	public ResponseEntity<Page<FoodItem>> searchFoodItems(@RequestBody JsonNode json) {

		String foodItemName = json.get("foodItemName").asText();
		String description = json.get("description").asText();
		String foodCategory = json.get("foodCategory").asText();
		int pageNumber = json.get("pageNumber").asInt();

		FoodItemPage foodItemPage = new FoodItemPage();
		foodItemPage.setPageNumber(pageNumber);

		var foodItemList = foodItemService.getFoodItemsUsingSearchTerms(foodItemName, description, foodCategory,
				foodItemPage);

		Page<FoodItem> newPage = foodItemList.map((e) -> removeAdditionalOffersFromFoodItem(e));

		return new ResponseEntity<>(newPage, HttpStatus.OK);
	}

	@PostMapping(value = "/getFoodItemObjectsForCart")
	public ResponseEntity<List<FoodItem>> getFoodItemObjectsForCart(@RequestBody JsonNode json) {

		ObjectMapper mapper = new ObjectMapper();
		Long[] tempArray = mapper.convertValue(json, Long[].class);
		List<Long> myIdList = Arrays.asList(tempArray);

		var foodItemList = foodItemService.findMultipleById(myIdList);

		foodItemList = ensureFoodItemHasMaxOneOffer(foodItemList);

		return new ResponseEntity<>(foodItemList, HttpStatus.OK);
	}

	@PostMapping(value = "/acceptFoodOrder")
	public ResponseEntity<JsonNode> acceptFoodOrder(@RequestBody JsonNode json) {

		var iterator = json.iterator();
		while (iterator.hasNext()) {

			var tempObject = iterator.next();

			var foodItemId = tempObject.get("foodItemId").asLong();
			var quantity = tempObject.get("quantity").asLong();
			foodItemInventoryReductionService.save(new FoodItemInventoryReductions(foodItemId, quantity));

		}

		var jsonResponse = createJsonNodeWithMessage("Accepted");
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/getRandomBestActiveOffers")
	public ResponseEntity<JsonNode> getRandomBestActiveOffers() {

		var offerList = supplyBestOfferPerCategory(offerService.findByStatus("Active"));

		Collections.shuffle(offerList);
		var returnList = new ArrayList<Offer>();
		int i = 0;
		for (Offer offer : offerList) {
			if (i == 3)
				break;
			returnList.add(offer);
			i++;
		}

		ObjectMapper mapper = new ObjectMapper();
		var jsonResponse = mapper.convertValue(returnList, JsonNode.class);

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	// utility method
	private JsonNode createJsonNodeWithMessage(String message) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("message", message);
		var response = mapper.convertValue(objectNode, JsonNode.class);
		return response;
	}


	// utility method
	private List<FoodItem> ensureFoodItemHasMaxOneOffer(List<FoodItem> listFoodItems) {
		
		for (FoodItem foodItem : listFoodItems) {
			foodItem = removeAdditionalOffersFromFoodItem(foodItem);
		}
		return listFoodItems;
	}

	// utility method
	private FoodItem removeAdditionalOffersFromFoodItem(FoodItem foodItem) {

		var previousOfferList = foodItem.getFoodCategory().getOffers().stream().filter((e) -> !e.getStatus().equalsIgnoreCase("Inactive")).collect(Collectors.toList());
		var newOfferList = supplyBestOfferPerCategory(previousOfferList);
		foodItem.getFoodCategory().setOffers(newOfferList.stream().collect(Collectors.toSet()));

		return foodItem;
	}
	
	// utility method
	private List<Offer> supplyBestOfferPerCategory(List<Offer> offerList) {
		List<Offer> bestOffersList = new ArrayList<>();

		Collections.sort(offerList, Comparator.comparingDouble(p -> ((Offer) p).getDiscountPercentage()).reversed());
		Map<String, List<Offer>> tempMap = offerList.stream().filter((e) -> !e.getStatus().equalsIgnoreCase("Inactive")).collect(Collectors.groupingBy(Offer::getCategory));
		tempMap.forEach((k, v) -> {
			if (v.size() > 0) {
				bestOffersList.add(v.get(0));
			}
		});
		return bestOffersList;
	}

}