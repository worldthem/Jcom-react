package jcom.cms.controllers.cp;

import jcom.cms.controllers.services.SubscribersService;
import jcom.cms.entity.Subscribers;
import jcom.cms.request.AdminBulk;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cp/")
@Log4j2
public class SubscribersControllerAdmin {
    @Autowired
    private SubscribersService service;
    @Value("${item.per.page.admin}")
    private Integer perPage;

    @GetMapping("/subscribers/get-list")
    public Page<Subscribers> getList(
            HttpServletRequest request,
            @RequestParam(value ="search", required=false ) String search,
            @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr) {

        return service.getList(search, PageRequest.of(pageNr, perPage));
    }


    /**
     * remove by id
     * @param id
     * @return
     */
    @DeleteMapping(path="/subscribers-remove") // Map ONLY POST Requests

    public String delete (@RequestParam(value ="id") Integer id ) {

        try{
            service.delete(id);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }


    /**
     * bulk here will be done, what's this,  well when the people delete more than one item
     * @param data
     * @return
     */
    @PostMapping(path="/subscribers-bulk-action", consumes = "application/json", produces = "application/json")
    public String updateBulk (@RequestBody AdminBulk data) {
        try{
            if(data.getId().size()>0 && data.getAction().contains("del")){

                for (String idnr : data.getId()) {
                    service.delete(Integer.parseInt(idnr));
                }

            }
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @GetMapping("/subscribers/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {

        List<Subscribers> all = service.findAll();
         response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"Subscribers.csv\"");
        try {
            OutputStream outputStream = response.getOutputStream();
            String outputResult = "";
            for(Subscribers v : all){
                outputResult=  outputResult.isEmpty() ? v.getEmail():  outputResult+",\n"+v.getEmail();
            }

            outputStream.write(outputResult.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            log.error("Cant create csv err:"+e.getMessage());
        }
    }


}
