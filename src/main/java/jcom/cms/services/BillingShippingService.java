package jcom.cms.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jcom.cms.entity.Settings;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.pojo.CheckoutField;
import jcom.cms.repositories.SettingsRepository;
import jcom.cms.request.SettingsIn;
import jcom.cms.response.BillingShippingFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

@Service
public class BillingShippingService {
    @Autowired
    private SettingsRepository settingsRepository;

    public BillingShippingFields getList(String fieldName){
        Settings settings = settingsRepository.findFirstByParam(fieldName);

        LinkedHashMap<String, CheckoutField> dbFields = new LinkedHashMap<>();
        LinkedHashMap<String, String> dbTitle = new LinkedHashMap<>();

        try{
            if(settings.getValue1() != null){
            Type type = new TypeToken<LinkedHashMap<String, CheckoutField>>(){}.getType();
            dbFields = (new Gson()).fromJson(settings.getValue1(), type);
            }
        }catch (Exception e){
            return null;
        }

        try{
            if(settings.getValue() != null){
                Type type = new TypeToken<LinkedHashMap<String, String>>(){}.getType();
                dbTitle = (new Gson()).fromJson(settings.getValue(), type);
            }
        }catch (Exception e){ }

     return new BillingShippingFields( HelpersFile.getJsonFileData(fieldName), dbFields, dbTitle );
   }

    public String update(String type, SettingsIn data){
          try{
            Settings settings = settingsRepository.findFirstByParam(type);
            settings= settings==null? new Settings() : settings;

            settings.setParam(type);
            settings.setValue(data.getValue());
            settings.setValue1(data.getValue1());
            settingsRepository.save(settings);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }


}
