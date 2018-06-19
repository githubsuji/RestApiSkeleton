package org.anthem.api.resources;

import java.util.List;

import org.anthem.api.common.annotations.RestApiController;
import org.anthem.api.jpa.resource.entities.Country;
import org.anthem.api.resources.services.CountryResponse;
import org.anthem.api.resources.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;


@RestApiController
@RequestMapping("/country")
public class CountryEndpoint {
	
	@Autowired
	private CountryService countryService;
	
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCountries(UriComponentsBuilder ucBuilder) {
		List<Country> countries = countryService.getCountries();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/country").buildAndExpand().toUri());
		return new ResponseEntity<>(countries, headers, HttpStatus.OK);

	}
	
	@GetMapping(value = "/{countryId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCountry(@PathVariable("countryId") Long countryId,
			UriComponentsBuilder ucBuilder) {
		Country country = countryService.getCountry(countryId);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/country").buildAndExpand(countryId).toUri());
		return new ResponseEntity<>(country, headers, HttpStatus.OK);

	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CountryResponse> addCountry(@RequestBody Country country,
			UriComponentsBuilder ucBuilder) {
		CountryResponse status = countryService.addCountry(country);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/country").buildAndExpand(country).toUri());
		return new ResponseEntity<CountryResponse>(status, headers, HttpStatus.OK);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CountryResponse> updateCountry(@RequestBody Country country, UriComponentsBuilder ucBuilder) {
		CountryResponse status = countryService.updateCountry(country);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/country").buildAndExpand(country).toUri());
		return new ResponseEntity<CountryResponse>(status, headers, HttpStatus.OK);
	}
	

	
	@DeleteMapping(value = "/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCountry(@PathVariable("countryId") Long countryId) {
		System.out.println("Fetching & Deleting Country against countryId " + countryId);

		Country currentCountry = countryService.getCountry(countryId);
		if (currentCountry == null) {
			System.out.println("Unable to delete Country with countryId " + countryId + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		countryService.deleteCountry(currentCountry.getCountryId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	

}
