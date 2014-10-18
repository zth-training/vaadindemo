package hu.zerotohero.dao;

import com.avaje.ebean.Ebean;
import hu.zerotohero.entities.Country;
import hu.zerotohero.entities.Region;

import java.util.List;


/**
 * @author Attila Budai <attila.budai@zerotohero.hu>
 * @author Adam Saghy <adam.saghy@webvalto.hu>
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */

public class CountryDao implements CountryDaoInterface {

	@Override
	public void saveCountryWithRegion(Country country, Region region) {
		Ebean.save(region);
		country.setRegion(region);
		Ebean.save(country);
	}

	@Override
	public void saveCountry(Country country) {
		Ebean.save(country);
	}

	@Override
	public void deleteCountry(Country country) {
		Ebean.delete(country);
	}

	@Override
	public Country findCountry(String id) {
		return Ebean.find(Country.class).where().eq("id", id).findUnique();
	}

	@Override
	public List<Country> findAllCountries() {
		return Ebean.find(Country.class).findList();
	}

	@Override
	public List<Region> findAllRegions() {
		return Ebean.find(Region.class).findList();
	}

}
