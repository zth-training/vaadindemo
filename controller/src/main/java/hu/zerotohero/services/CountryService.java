package hu.zerotohero.services;

import hu.zerotohero.dao.CountryDaoInterface;
import hu.zerotohero.entities.Country;
import hu.zerotohero.entities.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Saghy <adam.saghy@webvalto.hu>
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */
public class CountryService {

	//CountryDaoInterface lesz a típusa, hogy biztosítsuk a függetlenséget. Így bármelyik CountryDao implementációt tudja majd kezelni.
	private CountryDaoInterface countryDao;

	//Az application-context.xml-ben azt adtuk meg, hogy a konstruktoba paraméterként fogjuk átadni neki a countryDao-t.
	public CountryService(CountryDaoInterface countryDao) {
		this.countryDao = countryDao;
	}

	public Country createCountry(String id, Integer regionId, String name) {
		Country country = new Country();
		country.setId(id);
		Region region = new Region();
		region.setId(regionId);
		country.setRegion(region);
		country.setName(name);

		countryDao.saveCountry(country);
		return country;
	}

	public void deleteCountry(Country country) {
		countryDao.deleteCountry(country);
	}

	public Country findCountry(String id) {
		return countryDao.findCountry(id);
	}

	public List<Country> findAllCountriesFromOneRegion(Integer regionId) {
		List<Country> euCountries = new ArrayList<>();

		List<Country> allCountries = countryDao.findAllCountries();
		for (Country country : allCountries) {
			if (country.getRegion().getId().equals(regionId)) {
				euCountries.add(country);
			}
		}

		return euCountries;
	}

	public List<Region> findAllRegions() {
		return countryDao.findAllRegions();
	}

}
