package jcom.cms.controllers.cp;


import com.google.gson.Gson;
import jcom.cms.entity.Category;
import jcom.cms.entity.Pages;
import jcom.cms.entity.Product;
import jcom.cms.helpers.Helpers;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.request.AdminBulk;
import jcom.cms.response.PageFullResponse;
import jcom.cms.response.PagesListWithCatsResponse;
import jcom.cms.services.CategoryService;
import jcom.cms.services.PagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cp/")
public class PagesAdminController {

    @Autowired
    private PagesService service;

    @Value("${item.per.page.admin}")
    private Integer perPage;

    @Autowired
    private CategoryService categoryService;

    /**
     * will get the list of pages by type also will search by id or cat id
     * @param type
     * @param search
     * @param catid
     * @param request
     * @return
     */
    @GetMapping("/pages-list")
    public Page<Pages> showPages(@RequestParam(value ="type", required=false , defaultValue = "") String type,
                                 @RequestParam(value ="search", required=false, defaultValue = "") String search,
                                 @RequestParam(value ="catid", required=false , defaultValue = "") String catid,
                                 @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr,
                                 HttpServletRequest request
                                ) {



        if(!search.isEmpty()){
            return service.search(type, search, PageRequest.of(pageNr, perPage));
        }else if(!catid.isEmpty()){
            return service.getByTypeAndCatid(type, catid, PageRequest.of(pageNr, perPage));
        }else{
            return service.getByType(type, PageRequest.of(pageNr, perPage));
        }
   }


    /**
     * will get the list of pages by type also
     * @param type
     * @return
     */
    @GetMapping("/pages-list-all")
    public List<Pages> showAll(@RequestParam(value ="type", required=false , defaultValue = "") String type ) {
        return service.getByTypeList(type);
    }


    @GetMapping("/pages-list-with-categories")
    public PagesListWithCatsResponse showPagesWithCategories(@RequestParam(value ="type", required=false , defaultValue = "") String type,
                                                             @RequestParam(value ="search", required=false, defaultValue = "") String search,
                                                             @RequestParam(value ="catid", required=false , defaultValue = "") String catid,
                                                             @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr,
                                                             HttpServletRequest request
    ) {

       List<Category> categories = categoryService.getByTypeIn(type);

        if(!search.isEmpty()){
            return new PagesListWithCatsResponse(
                           service.search(type, search, PageRequest.of(pageNr, perPage)), categories);
        }else if(!catid.isEmpty()){
            return new PagesListWithCatsResponse(
                           service.getByTypeAndCatid(type, catid, PageRequest.of(pageNr, perPage)), categories);
        }else{
            return new PagesListWithCatsResponse(
                           service.getByType(type, PageRequest.of(pageNr, perPage)), categories);
        }
    }

    /**
     * will get on page
     * @param pageid
     * @return
     */
    @GetMapping("/pages-get-one")
    public Pages getOne(@RequestParam(value ="pageid", required=false , defaultValue = "new") String pageid){
          return pageid.contains("new") || pageid.compareTo("0") == 0 ? new Pages() :
                 service.getOne(Integer.parseInt(pageid));
    }


    /**
     * will get on page
     * @param pageid
     * @return
     */
    @GetMapping("/pages-get-one-with-categories")
    public PageFullResponse getOneWithCategories(@RequestParam(value ="pageid", required=false , defaultValue = "0") Integer pageid,
                                                 @RequestParam(value ="type", required=false , defaultValue = "posts") String type){
        return service.getOneFull(pageid,type);
    }

    /**
     * this part we duplicate the content from one language to other
     * @param
     * @return
     */
    @GetMapping("/pages-duplicate")
    public String duplicate(HttpServletRequest request){
        String  pageid = request.getParameter("pageid");
        String  langFrom = request.getParameter("langFrom");
        String  langTo = request.getParameter("langTo");

        return  service.duplicate(pageid, langFrom, langTo);
    }

    /**
     * Put the content in database
     * @param pages
     * @return
     */
    @PutMapping(path="/pages-insert-update", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody Pages pages) {


        try{
            service.updateInsert(pages);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }


    /**
     * remove by id
     * @param id
     * @return
     */
    @DeleteMapping(path="/pages-remove") // Map ONLY POST Requests

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

    @GetMapping(path="/pages-show-hide") // Map ONLY POST Requests

    public String status (@RequestParam(value ="id") Integer id, @RequestParam(value ="hide") Integer hide ) {

        try{
            Pages one = service.getOne(id) ;
            one.setStatus(hide);
            service.save(one);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    /**
     * bulk here will be done, what's this,  well when the people delete more than one item
     * @param request
     * @param data
     * @return
     */
    @PostMapping(path="/pages-bulk-action", consumes = "application/json", produces = "application/json")
    public String updateBulk (HttpServletRequest request, @RequestBody AdminBulk data) {

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


    /**
     * will get the suggestions blocks
     * @return
     */
    @GetMapping("/pages-get-ready-blocks")
    public Map<String, List<String>> getReadyBlocks(){

       return  service.getReadyBlocks();
    }

    /**
     * will update the suggestions blocks from api
     * @return
     */
    @GetMapping("/pages-update-ready-blocks")
    public Map<String, List<String>> updateReadyBlocks(){
         return  service.updateReadyBlocks();
    }

}
