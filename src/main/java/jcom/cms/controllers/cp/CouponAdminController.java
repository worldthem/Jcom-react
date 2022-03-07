package jcom.cms.controllers.cp;

import jcom.cms.entity.Coupons;
import jcom.cms.request.AdminBulk;
import jcom.cms.services.CouponServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cp/")
public class CouponAdminController {

    @Autowired // This means to get the bean called ProductRepository
    private CouponServices service;

    @Value("${item.per.page.admin}")
    private Integer perPage;


    @GetMapping(value = "/get-list-of-coupons")
    public Page<Coupons> getList(@RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr,
                                 @RequestParam(value ="search", defaultValue = "") String search) {
       return service.getListCoupons(search, PageRequest.of(pageNr, perPage));
    }

    /**
     * Update Insert Category
     * @param data
     * @return
     */
    @PutMapping(path="/coupon-insert-update", consumes = "application/json", produces = "application/json")
    public String saveCategory(@RequestBody Coupons data){
        return service.update(data);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(path="/coupon-remove") // Map ONLY POST Requests
    public String delete (@RequestParam(value ="id") Integer id ) {
        return service.delete(id);
    }


    @PostMapping(path="/coupons-action-bulk", consumes = "application/json", produces = "application/json")
    public String updateBulk (@RequestBody AdminBulk data) {

        try{
            if(data.getId().size()>0 && data.getAction().contains("del")){
                for (String idnr : data.getId()) {
                    service.delete(Integer.parseInt(idnr));
                }
            }
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }

}
