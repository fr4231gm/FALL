/*
 * EndorserRepository.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	//FindFixUpTaskPerCustomer
	@Query("select f from FixUpTask f where f.customer.id = ?1")
	Collection<FixUpTask> findFixUpTaskPerCustomer(int id);

	//FindFixUpTaskCustomer
	@Query("select f.applications from FixUpTask f where f.customer.id=?1")
	Collection<Application> findApplicationByCustomer(int id);

	@Query("select f from FixUpTask f where f.category.id = ?1")
	Collection<FixUpTask> findFixUpTasksByCategoryId(int id);

}
