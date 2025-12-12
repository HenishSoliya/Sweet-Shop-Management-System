package com.demo.SweetShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.SweetShop.model.Sweets;
import com.demo.SweetShop.service.SweetsService;

@RestController
@RequestMapping("api/sweets")
public class SweetsController {

	@Autowired
	private SweetsService service;

	@PostMapping()
	public ResponseEntity<String> addSweet(@RequestBody Sweets sweets) {
		return service.addSweet(sweets);
	}

	@GetMapping()
	public ResponseEntity<List<Sweets>> getSweets() {
		return service.getSweets();
	}

	@GetMapping("search/{name}/{category}/{low}to{high}")
	public ResponseEntity<List<Sweets>> getSweets(@PathVariable String name, @PathVariable String category,
			@PathVariable double low, @PathVariable double high) {
		return service.getSweets(name, category, low, high);
	}

	@PutMapping()
	public ResponseEntity<String> updateSweet(@RequestBody Sweets sweets) {
		return service.updateSweet(sweets);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteSweet(@PathVariable int id) {
		return service.deleteSweet(id);
	}

	@PostMapping("purchase/{id}/{purchase}")
	public ResponseEntity<String> purchaseSweets(@PathVariable int id, @PathVariable int purchase) {
		return service.purchaseSweets(id, purchase);
	}

	@PostMapping("restock/{id}/{restock}")
	public ResponseEntity<String> restockSweets(@PathVariable int id, @PathVariable int restock) {
		return service.restockSweets(id, restock);
	}

}
