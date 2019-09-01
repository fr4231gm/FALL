package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PrintSpooler;

@Repository
public interface PrintSpoolerRepository extends JpaRepository<PrintSpooler, Integer>{

	@Query("select p from PrintSpooler p where p.printer.id = ?1")
	PrintSpooler findByPrinterId(int printerId);

	@Query("select p from PrintSpooler p where p.printer.id in (select q.id from Inventory i join i.printers q where i.company.id = ?1)")
	Collection<PrintSpooler> findByCompanyId(int id);

	@Query("select p from PrintSpooler p join p.requests q where q.id = ?1")
	PrintSpooler findByRequestId(int id);

}
