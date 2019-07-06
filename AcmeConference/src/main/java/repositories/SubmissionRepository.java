package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository 
	@Query("select s from Submission s where s.paper.id = ?1") 
	@Query("select s from Submission s where s.conference.id = ?1") 
}