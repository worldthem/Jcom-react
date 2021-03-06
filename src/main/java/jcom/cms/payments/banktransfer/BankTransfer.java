package jcom.cms.payments.banktransfer;

import com.google.gson.Gson;
import jcom.cms.controllers.MainController;
import jcom.cms.entity.Settings;
import jcom.cms.helpers.View;
import jcom.cms.payments.Payment;
import jcom.cms.repositories.SettingsRepository;
import jcom.cms.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BankTransfer extends MainController implements Payment {
    @Autowired
    SettingsRepository settingsRepository;



    /**
     * default id enter name wthout space [Az09]
     * @return
     */
    public String getId(){
        return "banktransferPayment";
    }

    /**
     * the data in databasetable settings by param getId() in json format
     * @return
     */
    private BankTransferModule getSettings(){
        Settings settings = settingsRepository.findFirstByParam(getId());

        try{
            if(settings != null) {
                String val = settings.getValue1();
                return val.isEmpty()? new BankTransferModule() :  (new Gson()).fromJson(val, (Type) BankTransferModule.class);
            }
        }catch (Exception e){ }
        return new BankTransferModule();
    }

    /**
     * get title of payment
     * @return
     */
    public String getTitle(){
        return getSettings().getTitle();
    }

    /**
     * get description
     * @return
     */
    public String getDescription(){
        return getSettings().getDescription();
    }

    /**
     * this part will show the in the payment list on checkout page
     * @return
     */
    public String frontEnd(){
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("row", getSettings());
        templateModel.put("idPayment", getId());
        return templateHtml(templateModel, "payment::banktransfer/frontend");
    }

    /**
     * this part will show the admin options
     * @return
     */
    public String admin(){
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("row", getSettings());

        return templateHtml(templateModel, "payment::banktransfer/admin");
    }

    /**
     * store all the information from the admin config put in json and save in db settings
     * @param request
     * @return
     */
    public String store(MultipartHttpServletRequest request){
        String image = request.getParameter("image");
        MultipartFile file = request.getFile("file");

        // upload main image
        if(file != null){
            if(!file.getOriginalFilename().isEmpty()){
              image = (new StorageService()).uploadSingleImg(file, "content/views/payment/banktransfer/img");
            }
        }

        BankTransferModule moduleData =  getSettings();
        moduleData.setLang(request.getParameter("lang"));
        moduleData.setTitle(request.getParameter("title"));
        moduleData.setDescription(request.getParameter("description"));
        moduleData.setInstruction(request.getParameter("instruction"));
        moduleData.setNoimage(request.getParameter("noimage"));
        moduleData.setImage(image);

        Settings settings = settingsRepository.findFirstByParam(getId());
        if(settings  == null){
            settings = new Settings();
        }
        settings.setParam(getId());
        settings.setValue1((new Gson()).toJson(moduleData));
        settingsRepository.save(settings);

        return "";
    }

    /**
     * when costomers press checkout page
     * @return  = "pending" , "onhold", "success", "canceled", "redirect:https://url.com/url/data"
     */
    public String makePayment(HttpServletRequest request, Float totalcart, Integer orderId){
        return "pending";
    }
}