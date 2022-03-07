package jcom.cms.services;

import jcom.cms.entity.Coupons;
import jcom.cms.repositories.CouponAppliedRepository;
import jcom.cms.repositories.CouponRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CouponServices {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponAppliedRepository couponAppliedRepository;

    public Page<Coupons> getListCoupons(String search, Pageable pageable) {
        if(!search.isEmpty()){
            return couponRepository.findByTitleContainingIgnoreCase(search, pageable);
        }else {
            return couponRepository.findAll(pageable);
        }
    }

    public String update(Coupons data) {
        try{
            couponRepository.save(data);
            return "ok";
        }catch (Exception e){
            log.error("Insert Update coupon err: "+e.getMessage());
            return e.getMessage();
        }
    }

    public String delete(Integer id){

        try{
            couponRepository.deleteById(id);
            return "ok";
        }catch (Exception e){
            log.error("Remove coupon err: "+e.getMessage());
            return e.getMessage();
        }
      }

     public Coupons getOne(Integer id){
        return couponRepository.findFirstById(id);
     }
}
