package repositories;
import java.util.Collection; 
@Repository 
	@Query("select s from Sponsorship s where s.sponsor.id = ?1") 
}