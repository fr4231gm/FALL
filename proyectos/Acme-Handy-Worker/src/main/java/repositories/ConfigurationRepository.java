/*
 * ConfigurationRepository.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

	 @Query("select c.defaultBrands from Configuration c where c.id = ?1")
	    String findAllMakes(int id);

	 @Query("select c from Configuration c where c.lan = ?1")
	Configuration findByLanguage(String lan);
}
