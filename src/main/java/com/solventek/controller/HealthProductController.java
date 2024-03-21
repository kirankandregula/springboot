package com.solventek.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solventek.entity.Product;
import com.solventek.service.HealthCareService;

@RestController
@RequestMapping("/product/health")
@CrossOrigin(origins = "http://localhost:3000")
public class HealthProductController {

	@Autowired
    private  HealthCareService healthCareService;
    

	
	@GetMapping("/get")
	public List<Product> getHealthProducts() {
	    List<Product> allProducts = healthCareService.getProducts();
	    List<Product> healthProducts = new ArrayList<>();

	    for (Product product : allProducts) {
	        if ("Health".equals(product.getCategory())) {
	            healthProducts.add(product);
	        }
	    }

	    return healthProducts;
	}


    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return healthCareService.addProduct(product);
    }

    @GetMapping("/get/{id}")
    public Product getProductByid(@PathVariable Integer id) {
        return healthCareService.getProductByid(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return healthCareService.updateProduct(product, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        healthCareService.deleteProduct(id);
    }

  
}
