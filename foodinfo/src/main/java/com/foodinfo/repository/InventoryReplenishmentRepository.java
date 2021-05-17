package com.foodinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodinfo.domain.InventoryReplenishment;

public interface InventoryReplenishmentRepository extends JpaRepository<InventoryReplenishment, Long> {

}
