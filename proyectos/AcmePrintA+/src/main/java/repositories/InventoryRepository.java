package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer>{

	@Query("select i from Inventory i where i.company.id = ?1")
	Collection<Inventory> findInventoriesByCompanyId(int companyId);

	@Query("select a from Inventory a join a.printers p where p.id= ?1")
	Inventory findByPrinterId(int id);

	@Query("select a from Inventory a join a.spools p where p.id= ?1")
	Inventory findBySpoolId(int id);

	@Query("select a from Inventory a join a.spareParts p where p.id= ?1")
	Inventory findBySparePartId(int id);
	
}
