package jcom.cms.services;

import com.google.gson.Gson;
import jcom.cms.entity.Settings;
import jcom.cms.pojo.Attr;
import jcom.cms.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AttributesServices {

    @Autowired
    private SettingsRepository settingsRepository;

    public LinkedHashMap<String, Attr> getList(String type){
        Settings attr = settingsRepository.findFirstByParam("_attributes");
        /*
        LinkedHashMap<String, Attr> newAttr = new LinkedHashMap<>();

        attr.getAttributes().forEach((k,v)->{
            if(v.getType().contains(type))
                    newAttr.put(k,v);
        });*/

        return attr.getAttributes();
     }

    public String update(String json, String key){
       try{
        Settings attr = settingsRepository.findFirstByParam(key);
        attr= attr==null? new Settings() : attr;

        attr.setParam(key);
        attr.setValue1(json);

        settingsRepository.save(attr);
         return "ok";
       }catch (Exception e){
           return e.getMessage();
       }
    }

    public Map<String, Map<String,String>> getListSuggestion(String key) {
        Settings attr = settingsRepository.findFirstByParam(key);
        return attr==null? new HashMap<>() : attr.getValue1MapInMap();
    }


}
