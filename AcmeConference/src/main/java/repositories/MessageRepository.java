package repositories;
import java.util.Collection; 
@Repository 
	@Query("select m from Message m where m.sender.id = ?1") 
}