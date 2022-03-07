package jcom.cms.controllers.cp;

import jcom.cms.entity.Pages;
import jcom.cms.entity.Users;
import jcom.cms.mappingclass.ProductsList;
import jcom.cms.request.AdminBulk;
import jcom.cms.response.UsersResponse;
import jcom.cms.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/cp/")
public class UsersAdminController {

    @Value("${item.per.page.admin}")
    private Integer perPage;

    @Autowired
    private UsersService service;

    @GetMapping("/users/get-list")
    public Page<Users> getList(
            HttpServletRequest request,
            @RequestParam(value ="search", required=false ) String search,
            @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr) {

        return service.getList(search, PageRequest.of(pageNr, perPage));
    }

    /**
     * get user by id
     * @param userid
     * @return
     */
    @GetMapping("/users/get-one")
    public UsersResponse getOne(@RequestParam(value ="userid") Integer userid){
        return service.getOneWithRoles(userid);
    }


    /**
     * will insert or update existing row
      * @param users
     * @return
     */
    @PutMapping(path="/users-insert-update", consumes = "application/json", produces = "application/json")
    public String saveInsert(@RequestBody Users users){
       return service.insertUpdate(users);
    }

    /**
     * remove by id
     * @param id
     * @return
     */
    @DeleteMapping(path="/users-remove") // Map ONLY POST Requests

    public String delete (@RequestParam(value ="id") Integer id ) {

        try{
            service.delete(id);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    /**
     * Show hide page
     * @param id
     * @param hide
     * @return
     */

    @GetMapping(path="/users-show-hide") // Map ONLY POST Requests

    public String showHide (@RequestParam(value ="id") Integer id, @RequestParam(value ="hide") Integer hide ) {

        try{
            Users one = service.getOne(id) ;
            one.setLocked(hide==2 ? true : false);
            return service.save(one);
        }catch (Exception e){
            return e.getMessage();
        }

    }

    /**
     * bulk here will be done, what's this,  well when the people delete more than one item
     * @param data
     * @return
     */
    @PostMapping(path="/users-bulk-action", consumes = "application/json", produces = "application/json")
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

}
