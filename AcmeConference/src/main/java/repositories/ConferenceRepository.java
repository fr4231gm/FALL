package repositories;
import java.util.Collection; 
@Repository 
	@Query("select c from Conference c where c.administrator.id = ?1") 
	@Query("select c from Conference c where c.category.id = ?1") 
}