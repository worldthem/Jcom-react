package jcom.cms.repositories;



import jcom.cms.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepositorey extends JpaRepository<Media, Integer> {

    List<Media> findByProductid(Integer productid);
    List<Media> findByPageid(Integer pageid);
}
