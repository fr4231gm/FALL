package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FloatRepository extends JpaRepository<domain.Float, Integer> {
	
	//Find Float from Brotherhood
	@Query("select f from Float f where f.brotherhood.id = ?1")
	Collection<domain.Float> findFloatsByBrotherhoodId(int bthid);
	
}

