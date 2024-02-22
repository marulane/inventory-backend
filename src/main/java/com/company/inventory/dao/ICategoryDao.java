package com.company.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.inventory.model.Category;
//Hereda mediante CrudRepository todos los metodos de acceso a los datos
public interface ICategoryDao extends CrudRepository<Category, Long>{
	
	
}
