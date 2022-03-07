package jcom.cms.controllers.cp;

import jcom.cms.request.SettingsIn;
import jcom.cms.response.BillingShippingFields;
import jcom.cms.response.CurrenciesResponse;
import jcom.cms.services.BillingShippingService;
import jcom.cms.services.CurrenciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cp/")
public class CurrenciesAdminController {
    @Autowired // This means to get the bean called ProductRepository
    private CurrenciesService service;

    @GetMapping(value = "/get-list-of-currencies")
    public CurrenciesResponse getList() {
          return service.getList("_currencies");
    }


    @PutMapping(path = "/update-currencies", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody SettingsIn data ) {
         return service.update("_currencies", data);
    }
}
