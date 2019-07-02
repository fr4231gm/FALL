package repositories;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;import domain.Base;
@Repository public interface BaseRepository extends JpaRepository<Base, Integer> {
}