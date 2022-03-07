package jcom.cms.services;


import jcom.cms.entity.Settings;
import jcom.cms.helpers.Helpers;
import jcom.cms.pojo.MainOptions;
import jcom.cms.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService {
   @Autowired
    private SettingsRepository settingsRepository;

    public Settings findByParamAndValue2(String param, String val2){
        return  settingsRepository.findByParamAndValue2( param,  val2);
    }

    public Settings findFirstByParam(String param ){
        return  settingsRepository.findFirstByParam( param );
    }

    public Settings findById(Integer id ){
        return  settingsRepository.findFirstById(id);
    }

    public Settings save(Settings setting){
        return  settingsRepository.save(setting);
    }

    public List<Settings> findByParamInOrderByIdDesc(List<String> list){
        return  settingsRepository.findByParamInOrderByIdDesc(list);
    }
    public List<Settings> findByParam(String param){
        return settingsRepository.findByParamOrderByIdDesc(param);
    }

    public List<Settings> findAll(){
        return  settingsRepository.findAll();
    }

    public Settings getOne(Integer id){
        return  settingsRepository.getOne(id);
    }

    public List<Settings> getAutoload(){
        return  settingsRepository.findByAutoload("yes");
    }

    public void delete(Integer id){
         settingsRepository.deleteById(id);
    }

    public void deleteByParam(String param){
        settingsRepository.deleteByParam(param);
    }

    public long count(){
        return settingsRepository.count();
    }


    public String duplicate(String id, String langFrom, String langTo) {

        try{
            Settings row  = findById(Integer.parseInt(id));
            if(row !=null){
                row.setValue1(Helpers.duplicateContent(row.getValue1(), langFrom, langTo));
                save(row);
            }

            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public MainOptions getMainOpt(){
        Settings setting = settingsRepository.findFirstByParam("_main_settings");
        return setting.getMainOptions();
    }
}
