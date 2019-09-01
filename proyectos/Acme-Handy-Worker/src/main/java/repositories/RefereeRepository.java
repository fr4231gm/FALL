/*
 * UserAccountRepository.java
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

import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {
	
	@Query("Select r from Referee r where r.id=?1")
	Referee findByUserAccountId(int id);
	
}
