package repositories;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;import domain.Author;
@Repository public interface AuthorRepository extends JpaRepository<Author, Integer> {
}