package repositories;
import java.util.Collection; 
@Repository 
	@Query("select q from Quolet q where q.administrator.id = ?1") 
	@Query("select q from Quolet q where q.conference.id = ?1") 
}