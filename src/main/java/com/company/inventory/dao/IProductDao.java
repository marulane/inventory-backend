package com.company.inventory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.company.inventory.model.Product;

public interface IProductDao extends CrudRepository<Product, Long>{ //No integra el metodo de buscar por nombre
	
	
	/**
	 * 
	 * Me presento el mismo error.  Pero al parecer la consulta en Query en IProductDao no la toma como JPA si no como consulta SQL.

Sin embargo, en SQL estándar, no se puede utilizar el operador LIKE directamente con un parámetro de esta manera. Debes concatenar el valor del parámetro con los caracteres comodín % antes y después.

Aquí está la consulta en SQL: 

SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', ?1, '%')

A mi me funciono de esta manera aunque me quedaron dudas de como se debe usar la anotacion @Query
si como anotacion JPA o SQL?
	 */
	//@Query("select p from Product p  where p.name = like %?1%") //JPA
	
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', ?1, '%')")
	//https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
	List<Product> findByNameLike(String name);
	
	
	//Esta es otra forma de hacer lo mismo que el metodo de arriba
	List<Product> findByNameContainingIgnoreCase(String name);


}
