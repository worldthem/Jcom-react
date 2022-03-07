package jcom.cms.repositories;



import jcom.cms.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
     Category findFirstByCpuContaining(String cpu);
     List<Category> findByTypeContaining(String type);
     List<Category> findByTypeIn(Collection<String> types);
     Category getOne(Integer id);
     long count();

     List<Category> findByTypeIgnoreCaseOrderByCatidAscParentAsc(String type);

     Category findByCatid(Integer id);
}