package hu.zerotohero.dao;

import hu.zerotohero.entities.Country;
import hu.zerotohero.entities.Region;

import java.util.List;

/**
 * @author Adam Saghy <adam.saghy@webvalto.hu>
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */

//Ezt fogjuk használni mindenhol, hogy biztosítsuk az üzleti logika függetlenségét az entitáskezelőktől
public interface CountryDaoInterface {
	void saveCountryWithRegion(Country country, Region region);

	void saveCountry(Country country);

	void deleteCountry(Country country);

	Country findCountry(String id);

	List<Country> findAllCountries();

	List<Region> findAllRegions();
}
