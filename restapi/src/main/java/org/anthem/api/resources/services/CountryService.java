package org.anthem.api.resources.services;

import java.util.List;

import org.anthem.api.jpa.resource.entities.Country;

public interface CountryService {

	List<Country> getCountries();

	Country getCountry(Long countryId);

	CountryResponse addCountry(Country country);

	CountryResponse deleteCountry(Long countryId);

	CountryResponse updateCountry(Country country);
	

	

}

