package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SparePart;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer>{

}
