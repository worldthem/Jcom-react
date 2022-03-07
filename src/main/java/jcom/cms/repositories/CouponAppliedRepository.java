package jcom.cms.repositories;

import jcom.cms.entity.CouponsApplied;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponAppliedRepository extends JpaRepository<CouponsApplied, Integer> {

    CouponsApplied findByUseridAndOrdersid(String userid, Integer Ordersid);
    CouponsApplied findByOrdersid(Integer Ordersid);

}
