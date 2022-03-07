package jcom.cms.controllers.cp;

import com.google.gson.Gson;
import jcom.cms.entity.Pages;
import jcom.cms.entity.Settings;
import jcom.cms.payments.Payment;
import jcom.cms.repositories.SettingsRepository;
import jcom.cms.response.PaymentSettingResponse;
import jcom.cms.response.PaymnetsResponse;
import jcom.cms.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cp/")
public class PaymentsAdminController {

    @Autowired
    private PaymentsService service;



    @GetMapping("/payments/list")
    public PaymnetsResponse show() {
        return service.getList();
     }


    @GetMapping("/payments/settings")
    public PaymentSettingResponse single(@RequestParam(value ="id") String id) {
       return service.single(id);
    }


    @RequestMapping(value ="/payments/store", method = RequestMethod.PUT)
    public String store(@RequestParam(value ="payment") String payment, HttpServletRequest request, MultipartHttpServletRequest requestMultipart) {

         return service.store(payment, request, requestMultipart);
    }

    @GetMapping(path="/payments/show-hide") // Map ONLY POST Requests

    public String showHide (@RequestParam(value ="id") String id, @RequestParam(value ="hide") String hide ) {

        try{
            return service.showHide(id, hide);
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
