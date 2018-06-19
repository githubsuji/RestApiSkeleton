package org.anthem.api.resources.services;

import java.util.List;

import org.anthem.api.jpa.resource.entities.Country;
import org.anthem.api.repository.CountryRepository;
import org.anthem.api.resources.services.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private ServiceUtil serviceUtil;

	@Override
	public List<Country> getCountries() {

		List<Country> countries = countryRepository.findBy();

		return countries;
	}

	@Override
	public Country getCountry(Long countryId) {
		Country country = countryRepository.findByCountryId(countryId);
		return country;
	}

	@Override
	public CountryResponse addCountry(Country country) {
		// TODO Auto-generated method stub
		String status = null;
		CountryResponse response = new CountryResponse();
		// for(Designation designation : designations){
		Country d = countryRepository.save(country);
		status = "Country added successfully";
		status = d != null ? status : "Not added successfully";
		response.setCountry(d);
		response.setStatus(status);
		// }
		return response;
	}

	@Override
	public CountryResponse updateCountry(Country country) {
		String status = "Country updated successfully";
		Country d = countryRepository.save(country);
		CountryResponse response = new CountryResponse();
		status = d != null ? status : "Not updated successfully";
		response.setCountry(d);
		response.setStatus(status);
		return response;
	}
	@Override
	public CountryResponse deleteCountry(Long countryId) {
		String status = "Designation deleted successfully";
		countryRepository.delete(countryId);
		CountryResponse response = new CountryResponse();
		response.setStatus(status);
		return response;

	}

}
