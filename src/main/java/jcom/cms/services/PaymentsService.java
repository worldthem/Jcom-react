package jcom.cms.services;

import com.google.gson.Gson;
import jcom.cms.entity.Settings;
import jcom.cms.payments.Payment;
import jcom.cms.repositories.SettingsRepository;
import jcom.cms.response.PaymentSettingResponse;
import jcom.cms.response.PaymnetsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * in this class we do all the payment operations for admin part only like edit enable disable
 */
@Service
public class PaymentsService {
    @Autowired
    private List<Payment> paymentList;


    @Autowired
    SettingsRepository settingsRepository;

    private String settingsParam = "activePayments";

    /**
     * get the list of payments, each Module have to implement a Interface call Payment.js
     * by this List<Payment> paymentList we have individual access to module methods what can return or get data
     *
     * @return
     */
    public PaymnetsResponse getList() {
        Settings settings = settingsRepository.findFirstByParam(settingsParam);
        settings = settings  == null ? new Settings() : settings;
        List<String> list = settings.getValue1List();

        return new PaymnetsResponse(list,paymentList);
    }

    /**
     * edit page get information
     * @param id
     * @return
     */
    public PaymentSettingResponse single(String id) {

        PaymentSettingResponse response = new PaymentSettingResponse();

        for(Payment p:paymentList){
            if(p.getId().contains(id)) {
                response.setPayment(p);
                response.setAdminForm(p.admin());
                break;
            }
        }

        Settings settings = settingsRepository.findFirstByParam(settingsParam);
        settings = settings  == null ? new Settings() : settings;

        List<String> list = settings.getValue1List();
        response.setActivated(!list.contains(id) ? false: true);
        return response;
    }

    /**
     * when press the button save all the information go here and after we send it to the module
     * and add the id of payment to the list in database settings where param = paymenMethods
     * @param payment
     * @param request
     * @param requestMultipart
     * @return
     */
    public String store(String payment, HttpServletRequest request, MultipartHttpServletRequest requestMultipart) {

        for(Payment p:paymentList){
            if(p.getId().contains(payment)) {
                p.store(requestMultipart);
                break;
            }
        }

        String enable = request.getParameter("enable");
        showHide(payment, enable);

        return  "ok";
    }

    /**
     * if press the button enable than run this method
     * @param id
     * @param enable
     * @return
     */
    public String showHide(String id, String enable){

        Settings settings = settingsRepository.findFirstByParam(settingsParam);
        settings = settings  == null ? new Settings() : settings;

        List<String> list = settings.getValue1List();

        if(enable != null && enable.contains("yes")){
            if(!list.contains(id)){
                list.add(id);
            }
        }else{
            if(list.contains(id)){ list.remove(id);  }
        }

        settings.setParam(settingsParam);
        settings.setValue1((new Gson()).toJson(list));
        settingsRepository.save(settings);
        return "ok";
    }
}
