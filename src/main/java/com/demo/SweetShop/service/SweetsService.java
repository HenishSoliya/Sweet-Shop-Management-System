package com.demo.SweetShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.SweetShop.model.Sweets;
import com.demo.SweetShop.repository.SweetsRepository;

@Service
public class SweetsService {

	@Autowired
	private SweetsRepository repository;

	public ResponseEntity<String> addSweet(Sweets sweets) {
		try {
			repository.save(sweets);
			return new ResponseEntity<String>("Data successfully inserted.", HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>("Data insertion failed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<Sweets>> getSweets() {
		return new ResponseEntity<List<Sweets>>(repository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<List<Sweets>> getSweets(String name, String category, double low, double high) {
		if (name.equals("*"))
			name = "";
		if (category.equals("*"))
			category = "";
		if (high == 0)
			return new ResponseEntity<List<Sweets>>(
					repository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category),
					HttpStatus.OK);
		return new ResponseEntity<List<Sweets>>(
				repository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndPriceBetween(name, category,
						low, high),
				HttpStatus.OK);

	}

	public ResponseEntity<String> updateSweet(Sweets sweets) {
		try {
			repository.save(sweets);
			return new ResponseEntity<String>("Data successfully updated.", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>("Data updation failed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> deleteSweet(int id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<String>("Data successfully deleted.", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>("Data deletation failed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<String> purchaseSweets(int id, int purchase) {
		try {
			int availableStock = repository.findById(id).get().getQuantity();
			if (availableStock < purchase)
				return new ResponseEntity<String>("Purchase failed: only " + availableStock + " units are available.",
						HttpStatus.BAD_REQUEST);
			repository.updatePurchaseAmount(id, purchase);
			return new ResponseEntity<String>("Purchase successful.", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>("Purchase failed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> restockSweets(int id, int restock) {
		try {
			repository.updateRestockAmount(id, restock);
			return new ResponseEntity<String>("Restock entry successful.", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>("Restock entry failed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
