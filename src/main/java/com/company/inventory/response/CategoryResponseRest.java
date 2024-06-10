package com.company.inventory.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase sirve para la respuesta al cliente
 */

@Getter
@Setter
public class CategoryResponseRest extends ResponseRest{
	private CategoryResponse categoryResponse = new CategoryResponse();

}
