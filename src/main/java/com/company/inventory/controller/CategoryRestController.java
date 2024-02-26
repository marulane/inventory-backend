package com.company.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

//Clase controladora que expone el servicio como tipo Rest

@RestController
@RequestMapping("/api/v1") //mapping general que tendran todos los servicios de este controlador
public class CategoryRestController {
	
	@Autowired
	private ICategoryService service;

	
	//Este metodo inyecta la interfaz de servicio que ira al metodo search() y este metodo estara implementado en la clase serviceImpl
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> seaarchCategories(){
		
		ResponseEntity<CategoryResponseRest> response = service.search();
		return response;
		
		
	}
	
	/**
	 * get all the categories by id
	 * @param id
	 * @return
	 */
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id){
		
		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		return response;
		
		
	}
	
	/**
	 * save a category
	 * @param category
	 * @return
	 */
	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> saveCategory(@RequestBody Category category){
		
		ResponseEntity<CategoryResponseRest> response = service.saveCategory(category);
		return response;
		
		
	}
}
