package jcom.cms.controllers.cp;

import com.google.gson.Gson;
import jcom.cms.pojo.Attr;
import jcom.cms.repositories.SettingsRepository;
import jcom.cms.request.SettingsIn;
import jcom.cms.response.BillingShippingFields;
import jcom.cms.services.BillingShippingService;
import jcom.cms.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/v1/cp/")
public class BillingShippingAdminController {
    @Autowired // This means to get the bean called ProductRepository
    private BillingShippingService service;

    @GetMapping(value = "/get-billing-shipping-data/{type}")
    public BillingShippingFields getList(@PathVariable String type) {
          return service.getList(type);
    }


    @PutMapping(path = "/update-billing-shipping/{type}", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody SettingsIn data, @PathVariable String type) {
         return service.update(type, data);
    }
}
