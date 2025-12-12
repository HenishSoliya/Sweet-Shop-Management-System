package com.demo.SweetShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.SweetShop.model.Sweets;

import jakarta.transaction.Transactional;

public interface SweetsRepository extends JpaRepository<Sweets, Integer> {

	List<Sweets> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndPriceBetween(String name,
			String category, Double minPrice, Double maxPrice);

	List<Sweets> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);

	@Modifying
	@Transactional
	@Query(value = "UPDATE sweets SET quantity = quantity - :amount WHERE id = :id", nativeQuery = true)
	int updatePurchaseAmount(@Param("id") int id, @Param("amount") int amount);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE sweets SET quantity = quantity + :amount WHERE id = :id", nativeQuery = true)
	int updateRestockAmount(@Param("id") int id, @Param("amount") int amount);

}
