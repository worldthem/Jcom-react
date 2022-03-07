package jcom.cms.controllers.cp;

import com.google.gson.Gson;
import jcom.cms.entity.Settings;
import jcom.cms.helpers.Helpers;
import jcom.cms.response.EditLangResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cp/")
@Log4j2
public class LanguageAdminController {

    @Value("${public.path}")
    private String publicPath;

    @GetMapping("/get-lang")
    public EditLangResponse getData(@RequestParam(value ="lang", required=false ) String lang){

        return new EditLangResponse(Helpers.getEnLang(), Helpers.getLang(lang));
    }


    @PutMapping(path="/update-lang-file", consumes = "application/json", produces = "application/json")
    public String save(@RequestBody Map<String,String> data, @RequestParam(value ="lang", required=false ) String lang){
        try{
            Helpers.updateFile(new Gson().toJson(data), publicPath+"lang/"+lang+".json");
            return "ok" ;
        }catch (Exception e){
            log.error("error update/insert lang:"+e.getMessage());
            return e.getMessage();
        }
    }


}
