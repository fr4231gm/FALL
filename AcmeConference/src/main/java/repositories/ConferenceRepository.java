package repositories;import java.util.Collection;import java.util.Date;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import domain.Conference;@Repositorypublic interface ConferenceRepository extends JpaRepository<Conference, Integer> {	@Query("select c from Conference c where c.administrator.id = ?1")	Collection<Conference> findConferencesByAdministratorId(int administratorId);	@Query("select c from Conference c where c.category.id = ?1")	Collection<Conference> findConferencesByCategoryId(int categoryId);	@Query("select c from Conference c where (c.startDate <= ?1 and c.endDate >= ?1) and (c.isDraft = false)")	Collection<Conference> findRunningConferences(Date actual);	@Query("select c from Conference c where (c.startDate <= ?2 and c.endDate >= ?2) and (c.isDraft = false) and (c.title like %?1% or c.summary like %?1% or c.venue like %?1%) and (c.isDraft = false)")	Collection<Conference> filterRunning(String keyword, Date actual);}