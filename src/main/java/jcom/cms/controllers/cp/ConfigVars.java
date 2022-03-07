package jcom.cms.controllers.cp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jcom.cms.entity.Settings;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.helpers.Language;
import jcom.cms.pojo.Currency;
import jcom.cms.pojo.MainOptions;
import jcom.cms.response.ConfigData;
import jcom.cms.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class ConfigVars {

    @Autowired
    private SettingsService service;

    @RequestMapping(value ="/get-settings-vars", method = RequestMethod.GET)
    public ConfigData showproductlist(HttpServletRequest request ) {
           String lang = request.getParameter("lang");
           String isAdmin = request.getParameter("isadmin");

           List<Settings> settings = service.getAutoload();
           ConfigData data= new ConfigData();
           data.setSettings(settings);


          for(Settings val : settings){
              // set Main options
            if(val.getParam().contains("_main_settings")){
               data.setMainOptions(val.getMainOptions());
            }

             //set currencies
            if(val.getParam().contains("_currencies")){

                data.setCurrencies(getCurrency(val.getValue1()));
                data.setCurrency(getMainCurrency(data.getCurrencies()));
            }
          }

           data.setLanguages(data.getMainOptions().getLanguages());

          String langFileName ="";
          if(isAdmin != null && isAdmin.contains("yes")){
               langFileName = data.getMainOptions().getAdminlang();
          }else{
              String defoultLang =  data.getMainOptions().getLanguages().get(0);
               langFileName = lang!=null && !lang.isEmpty() && lang.compareTo("en") !=0  ? lang : defoultLang;
          }


         if(langFileName.compareTo("en") !=0){
             data.setTranslate(Language.getLang(langFileName));
          }

         return data;
    }

    private Currency getMainCurrency(List<Currency> curencies){
        Currency main = null;
        for(Currency currency : curencies){
          if(currency.getMain())
              main= currency;
        }

        return main !=null ? main : curencies.size() >0 ? curencies.get(0) : new Currency();
    }

    private List<Currency> getCurrency(String json){

        try {
            Type type = new TypeToken<List<Currency>>(){}.getType();
            return json==null || json.isEmpty() ? new ArrayList<>() :
                                     (new Gson()).fromJson(json, type);
        }catch (Exception e){}

        return new ArrayList<>();
    }

}
