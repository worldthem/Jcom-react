package jcom.cms.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jcom.cms.entity.Settings;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.pojo.CheckoutField;
import jcom.cms.pojo.Currency;
import jcom.cms.repositories.SettingsRepository;
import jcom.cms.request.SettingsIn;

import jcom.cms.response.BillingShippingFields;
import jcom.cms.response.CurrenciesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

@Service
public class CurrenciesService {

    @Autowired
    private SettingsRepository settingsRepository;

    public CurrenciesResponse getList(String fieldName){
        Settings settings = settingsRepository.findFirstByParam(fieldName);

        LinkedHashMap<String, Currency> dbFields = new LinkedHashMap<>();


        try{
            if(settings.getValue1() != null){
                Type type = new TypeToken<LinkedHashMap<String, Currency>>(){}.getType();
                dbFields = (new Gson()).fromJson(settings.getValue1(), type);
            }
        }catch (Exception e){ }


        return new CurrenciesResponse( HelpersFile.getJsonFileData("currencies"), dbFields  );
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
