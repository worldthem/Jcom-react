package jcom.cms.repositories;

import jcom.cms.entity.Pages;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PagesRepository extends CrudRepository<Pages, Integer> {
    Pages findFirstByCpuAndType(String cpu, String type);

    List<Pages> findByTypeOrderByPageidDesc(String type);
    Page<Pages> findByTypeOrderByPageidDesc(String type, Pageable pageable);

    Page<Pages> findByTypeAndCategories_CatidOrderByPageidDesc(String type, Integer catid, Pageable pageable);
    Page<Pages> findByTypeAndTitleContainsIgnoreCaseOrderByPageidDesc(String type, String search, Pageable pageable);


    Pages findFirstByDirection(String direction);
    Pages getOne(Integer id);
    long count();
    Page<Pages> findByTitleContainingOrTextContaining(String txt1, String txt2,   Pageable pageable);

    Pages findByPageid(Integer id);
}