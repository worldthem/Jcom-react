package jcom.cms.controllers.cp;

import jcom.cms.entity.Settings;
import jcom.cms.response.ShippingMethodsResponse;
import jcom.cms.services.SettingsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cp/")
@Log4j2
public class SettingsAdminController {
    @Autowired
    private SettingsService service;


    @GetMapping("/get-settings-by-param")
    public List<Settings> getAll(@RequestParam(value ="param" ) String param){
        return service.findByParam(param);
    }

    @GetMapping("/get-one-line-settings-by-param")
    public Settings getOneByParam(@RequestParam(value ="param" ) String param){
        return service.findFirstByParam(param);
    }

    @GetMapping("/get-one-line-settings-by-id")
    public Settings getOneById(@RequestParam(value ="id" ) Integer id){
        return service.findById(id);
    }

    /**
     * Update Insert
     * @param data
     * @return
     */
    @PutMapping(path="/insert-update-settings", consumes = "application/json", produces = "application/json")
    public String save(@RequestBody Settings data){
        try{
            service.save(data);
            return "ok" ;
        }catch (Exception e){
            log.error("error update/insert settings:"+e.getMessage());
            return e.getMessage();
        }
   }



    @DeleteMapping("/remove-settings-by-param")
    public String deleteByParam(@RequestParam(value ="param" ) String param){
        try{
            service.deleteByParam(param);
            return "ok" ;
        }catch (Exception e){
            log.error("error remove setting by param:"+e.getMessage());
            return e.getMessage();
        }
    }

    @DeleteMapping("/remove-settings-by-id")
    public String deleteByid(@RequestParam(value ="id" ) Integer id){
        try{
            service.delete(id);
            return "ok" ;
        }catch (Exception e){
            log.error("error remove setting by id:"+e.getMessage());
            return e.getMessage();
        }
    }


    /**
     * this part we duplicate the content from one language to other
     * @param
     * @return
     */
    @GetMapping("/settings-duplicate")
    public String duplicate(HttpServletRequest request){
        String   id = request.getParameter("id");
        String  langFrom = request.getParameter("langFrom");
        String  langTo = request.getParameter("langTo");

        return  service.duplicate(id, langFrom, langTo);
    }
}
