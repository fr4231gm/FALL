package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Printer;

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Integer>{
	
	//Find active printers
	@Query("select p from Printer p where p.isActive=true")
	Collection<Printer> findActivePrinters();

	@Query("select i.printers from Inventory i where i.company.id = ?1")
	Collection<Printer> findByCompanyId(int id);

	@Query("select p from Inventory i join i.printers p where i.company.id = ?1 and p.isActive = true")
	Collection<Printer> findActiveByCompanyId(int companyId);

}


