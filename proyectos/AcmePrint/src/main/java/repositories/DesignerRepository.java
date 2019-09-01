package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Designer;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Integer>{

	@Query("select d from Designer d where d.userAccount.id = ?1")
	Designer findDesignerByUserAccountId(int userAccountId);
	
	@Query("select d from Designer d where d.userAccount.username = ?1")
	Designer findDesignerByUsername(String username);
}
