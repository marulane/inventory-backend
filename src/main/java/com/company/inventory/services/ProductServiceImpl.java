package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;

@Service
public class ProductServiceImpl implements IProductService {
	
	//Inyeccion de deoendencia usando constructor con la herramienta source, generate constructor using fields
	//se realiza para poder acceder al crud de la interfaz de categorias, ya que se utilizara el metodo search category en el metodo save de esta clase
	//Es una alternativa a la anotacion @Autowired
	//Esta forma de inyeccion sirve mejor para realizar pruebas unitarias
	private ICategoryDao categoryDao;
	private IProductDao productDao;
	

	public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao = productDao;
	}


	@Override
	@Transactional //Debe ser la de spring framework
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			//search category to set in the product object
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if(category.isPresent()) {
				product.setCategory(category.get());
			}else {
				response.setMetadata("Respuesta nok", "-1", "Categoría asociada al producto no encontrada");
				return new ResponseEntity <ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			//save the product
			Product productSaved = productDao.save(product);
			
			if(productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto guardado");

			}else {
				response.setMetadata("Respuesta nok", "-1", "producto no guardado");
				return new ResponseEntity <ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			e.getStackTrace(); //para agregar mas informacion del error en el log
			response.setMetadata("Respuesta nok", "-1", "Error al guardar producto");
			return new ResponseEntity <ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity <ProductResponseRest>(response, HttpStatus.OK);
	}


	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<ProductResponseRest> searchById(Long productId) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			//search product by Id
			Optional<Product> product = productDao.findById(productId);
			
			if(product.isPresent()) {

				byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
				product.get().setPicture(imageDescompressed);
				list.add(product.get());
				response.getProduct().setProducts(list);
				
				response.setMetadata("Respuesta ok", "00", "Producto encontrado");
			
			}else {
				response.setMetadata("Respuesta nok", "-1", "Producto no encontrado");
				return new ResponseEntity <ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		
		}catch(Exception e) {
			e.getStackTrace(); //para agregar mas informacion del error en el log
			response.setMetadata("Respuesta nok", "-1", "Error al buscar producto");
			return new ResponseEntity <ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity <ProductResponseRest>(response, HttpStatus.OK);
	}


	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<ProductResponseRest> searchByName(String productName) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();

		
		try {
			//search product by Name
			listAux = productDao.findByNameContainingIgnoreCase(productName);
						
			if( listAux.size() > 0) {
				
				listAux.stream().forEach((p)->{
					byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imageDescompressed);
					list.add(p);
				});

				
				response.getProduct().setProducts(list);
				
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
			
			}else {
				response.setMetadata("Respuesta nok", "-1", "Productos no encontrados");
				return new ResponseEntity <ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		
		}catch(Exception e) {
			e.getStackTrace(); //para agregar mas informacion del error en el log
			response.setMetadata("Respuesta nok", "-1", "Error al buscar producto por nombre");
			return new ResponseEntity <ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity <ProductResponseRest>(response, HttpStatus.OK);
	}

}
