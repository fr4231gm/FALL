
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

	@Query("select f from File f where f.invoice.id = ?1")
	File findByInvoice(int invoiceId);

}
