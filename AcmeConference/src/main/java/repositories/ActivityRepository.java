package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository 
	@Query("select a from Activity a where a.conference.id = ?1") 
}