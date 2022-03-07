package jcom.cms.controllers.cp;

import jcom.cms.entity.Country;
import jcom.cms.entity.Product;
import jcom.cms.entity.Shipping;
import jcom.cms.request.AdminBulk;
import jcom.cms.response.ShippingMethodsResponse;
import jcom.cms.services.CountryService;
import jcom.cms.services.ShippingMethodsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/cp/")
public class ShippingMethodsController {

    @Autowired
    private ShippingMethodsServices service;

    @Value("${item.per.page.admin}")
    private Integer perPage;

    @GetMapping("/get-all-shipping-methods")
    public ShippingMethodsResponse getAll(@RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr){
         return service.getList(PageRequest.of(pageNr, perPage));
    }



    /**
     * Update Insert Category
     * @param data
     * @return
     */
    @PutMapping(path="/insert-update-shipping-method", consumes = "application/json", produces = "application/json")
    public String saveCategory(@RequestBody Shipping data){
        return service.update(data);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(path="/shipping-method-remove") // Map ONLY POST Requests
    public String delete (@RequestParam(value ="id") Integer id ) {
        return service.delete(id);
    }


    @PostMapping(path="/shipping-method-action", consumes = "application/json", produces = "application/json")
    public String updateBulk (HttpServletRequest request, @RequestBody AdminBulk data) {

        try{
            if(data.getId().size()>0 && data.getAction().contains("del")){
                for (String idnr : data.getId()) {
                    service.delete(Integer.parseInt(idnr));
                }
            }else if(data.getId().size()>0 &&
                    (data.getAction().contains("1") || data.getAction().contains("2"))){
                for (String idnr : data.getId()) {
                    Shipping entity = service.getOne(Integer.parseInt(idnr)) ;
                    entity.setStatus(Integer.parseInt(data.getAction()));
                    service.save(entity);
                }
            }
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @GetMapping(path="/shipping-method-show-hide") // Map ONLY POST Requests

    public String status (@RequestParam(value ="id") Integer id, @RequestParam(value ="hide") Integer hide ) {

        try{
            Shipping entity = service.getOne(id) ;
            entity.setStatus(hide);
            service.save(entity);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
