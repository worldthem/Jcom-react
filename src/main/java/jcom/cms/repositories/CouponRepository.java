package jcom.cms.repositories;

import jcom.cms.entity.Coupons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Integer> {
    Page<Coupons> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Coupons> findAllByOrderByIdDesc(Pageable pageable);
    Coupons findFirstById(Integer id);
}
