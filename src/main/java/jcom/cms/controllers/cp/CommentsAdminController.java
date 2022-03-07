package jcom.cms.controllers.cp;

import jcom.cms.entity.Comments;
import jcom.cms.entity.Orders;
import jcom.cms.entity.Pages;
import jcom.cms.mappingclass.CommentsList;
import jcom.cms.request.AdminBulk;
import jcom.cms.services.CommentsService;
import jcom.cms.services.OrdersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1/cp/")
public class CommentsAdminController {

    @Autowired
    private CommentsService service;

    @Value("${item.per.page.admin}")
    private Integer perPage;

    @GetMapping("/get-list-of-comments")
    public Page<CommentsList> getOneById(HttpServletRequest request,
                                         @RequestParam(value ="status", defaultValue = "0") Integer status,
                                         @RequestParam(value ="search", defaultValue = "") String search,
                                         @RequestParam(value ="userid", defaultValue = "0") Integer userid,
                                         @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr
    ){

       return service.getList(status, userid, search, PageRequest.of(pageNr, perPage));
    }


    /**
     * remove by id
     * @param id
     * @return
     */
    @DeleteMapping(path="/comment-remove") // Map ONLY POST Requests

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

    @GetMapping(path="/comment-show-hide") // Map ONLY POST Requests

    public String showHide (@RequestParam(value ="id") Integer id, @RequestParam(value ="hide") Integer hide ) {

        try{
            Comments one = service.getOne(id) ;
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
    @PostMapping(path="/comments-bulk-action", consumes = "application/json", produces = "application/json")
    public String updateBulk (HttpServletRequest request, @RequestBody AdminBulk data) {

        try{
            if(data.getId().size()>0 && data.getAction().contains("del")){
                 for (String idnr : data.getId()) {
                    service.delete(Integer.parseInt(idnr));
                 }
            }else if(data.getId().size()>0 && (data.getAction().contains("1") || data.getAction().contains("2"))){
                 for (String idnr : data.getId()) {
                     Comments row = service.getOne(Integer.parseInt(idnr)) ;
                     row.setStatus(Integer.parseInt(data.getAction()));
                     service.save(row);
                 }

            }
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
