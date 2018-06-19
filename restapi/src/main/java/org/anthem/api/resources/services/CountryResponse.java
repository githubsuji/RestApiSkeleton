package org.anthem.api.resources.services;


import org.anthem.api.jpa.resource.entities.Country;

import lombok.Data;

@Data
public class CountryResponse {
	private String status;
	private Country country;
	
	
}
