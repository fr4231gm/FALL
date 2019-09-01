package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {
	
	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2")
	Box getSystemBoxByName(int id, String name);

	@Query("select b from Box b where b.actor.id = ?1")
	Collection<Box> findByActor(int id);

	@Query("select b from Box b where b.parentBox.id = ?1")
	Collection<Box> getchilds(int id);

	@Query("select b from Box b where b.actor.id = ?1 and b.parentBox = null")
	Collection<Box> findRootBoxesByActor(int id);
}

