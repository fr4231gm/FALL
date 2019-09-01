package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from FixUpTask f where (f.ticker like %?1% or f.address like %?1% or f.description like %?1%) and f.category.id=?2 and f.warranty.id=?3 and f.maximumPrice>=?4 and f.maximumPrice<=?5 and f.startDate>=?6 and f.endDate<=?7")
	Collection<FixUpTask> filter(String keyWord, int category, int warranty, Double priceMin, Double priceMax, Date startDate, Date endDate);

	@Query("select f from FixUpTask f where (f.ticker like %?1% or f.address like %?1% or f.description like %?1%) and f.warranty.id=?2 and f.maximumPrice>=?3 and f.maximumPrice<=?4 and f.startDate>=?5 and f.endDate<=?6")
	Collection<FixUpTask> filterNoCategory(String keyWord, int warranty, Double priceMin, Double priceMax, Date startDate, Date endDate);

	@Query("select f from FixUpTask f where (f.ticker like %?1% or f.address like %?1% or f.description like %?1%) and f.category.id=?2 and f.maximumPrice>=?3 and f.maximumPrice<=?4 and f.startDate>=?5 and f.endDate<=?6")
	Collection<FixUpTask> filterNoWarranty(String keyWord, int category, Double priceMin, Double priceMax, Date startDate, Date endDate);

	@Query("select f from FixUpTask f where (f.ticker like %?1% or f.address like %?1% or f.description like %?1%) and f.maximumPrice>=?2 and f.maximumPrice<=?3 and f.startDate>=?4 and f.endDate<=?5")
	Collection<FixUpTask> filterNoCategoryWarranty(String keyWord, Double priceMin, Double priceMax, Date startDate, Date endDate);

	@Query("select f from Finder f where f.handyWorker.id=?1")
	Finder findOneByPrincipal(int id);

	@Query("select f from Finder f where f.category.id=?1")
	Collection<Finder> findByCategory(int id);
}
