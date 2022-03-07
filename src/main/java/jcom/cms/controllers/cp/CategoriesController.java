package jcom.cms.controllers.cp;

import jcom.cms.entity.Category;
import jcom.cms.request.AdminBulk;
import jcom.cms.response.CategoriesSingle;
import jcom.cms.response.InsertCategory;
import jcom.cms.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cp/")
public class CategoriesController {
    @Autowired // This means to get the bean called ProductRepository
    private CategoryService service;


    @GetMapping("/get-all-categories")
    private List<Category> getAll(){
        return service.getAll();
    }


    @GetMapping("/get-categories-by-type")
    private List<Category> getByType(@RequestParam(value ="type") String type){
        return service.getByType(type);
    }

    /**
     * Get single category by ID
     * @param id
     * @return
     */

    @GetMapping("/get-category")
    private CategoriesSingle getSingle(@RequestParam(value ="id") Integer id,
                                       @RequestParam(value ="type") String type){

       return service.getSingle(id,type);
     }


    /**
     * Update Insert Category
     * @param data
     * @return
     */
    @PutMapping(path="/insert-update-category", consumes = "application/json", produces = "application/json")
    private String saveCategory(@RequestBody Category data){
        try{
            service.updateInsert(data);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
         }

    }

    /**
     * Update Insert Category
     * @param data
     * @return
     */
    @PutMapping(path="/categories-insert-short", consumes = "application/json", produces = "application/json")
    private InsertCategory insertCategoryFromRightSidebar(@RequestBody Map<String, String> data){
          return service.insertShort(data);
     }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(path="/category-remove") // Map ONLY POST Requests

    public String delete (@RequestParam(value ="id") String id ) {

        try{
            service.delete(id);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }


    /**
     * Show hide category
     * @param id
     * @param hide
     * @return
     */
    @GetMapping(path="/category-show-hide") // Map ONLY POST Requests
     public String status(@RequestParam(value ="id") Integer id, @RequestParam(value ="hide") Integer hide ) {

        try{
            Category cat = service.getOne(id) ;
            cat.setStatus(hide);
            service.save(cat);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @PostMapping(path="/categories-action", consumes = "application/json", produces = "application/json")
    public String updateBulk (HttpServletRequest request, @RequestBody AdminBulk data) {

        try{
            if(data.getId().size()>0 && data.getAction().contains("del")){

                for (String idnr : data.getId()) {
                    service.delete(idnr);
                }

            }
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }


}
