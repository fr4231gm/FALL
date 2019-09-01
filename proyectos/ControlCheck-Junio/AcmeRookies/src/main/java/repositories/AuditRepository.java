
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	//FINDALL Audits By AuditorId
	@Query("select a from Audit a where a.auditor.id = ?1")
	Collection<Audit> findAuditsByAuditorId(int auditorId);

	//FIND All Audits By AuditorId with isDraft=false and PositionId
	@Query("select a from Audit a where a.auditor.id = ?1 and a.isDraft=false and a.position.id = ?2")
	Collection<Audit> findPublishedAuditsByAuditorIdAndPositionId(int auditorId, int positionId);

	//FIND All Audits in Final Mode
	@Query("select a from Audit a where a.isDraft=false")
	Collection<Audit> findAllPublishedAudits();

	//FIND All Audits published by company
	@Query("select a from Audit a where a.position.company.id=?1 and a.isDraft=false")
	Collection<Audit> findAllPublishedAuditsByCompany(int companyId);
	
	//FIND All Audits published by position
	@Query("select a from Audit a where a.position.id=?1 and a.isDraft=false")
	Collection<Audit> findAllPublishedAuditsByPositionId(int positionId);

	//FIND All Audits by position
	@Query("select a from Audit a where a.position.id=?1")
	Collection<Audit> findByPosition(int positionId);

}
