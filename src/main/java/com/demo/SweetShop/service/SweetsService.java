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


}
