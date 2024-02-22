package com.company.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;

//Esta clase, mediante su estructura sirve para devolver la seccion de metadata de una respuesta determinada
public class ResponseRest {
	
	private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();
	
	

	public ArrayList<HashMap<String, String>> getMetadata() {
		return metadata;
	}

	public void setMetadata(String type, String code, String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("type", type);
		map.put("code", code);
		map.put("date", date);
		
		metadata.add(map);
	}
	

}
