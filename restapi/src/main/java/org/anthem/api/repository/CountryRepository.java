package org.anthem.api.repository;

import java.util.List;

import org.anthem.api.jpa.resource.entities.Country;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {


	Country findByCountryId(Long countryId);
	List<Country> findBy();
}
