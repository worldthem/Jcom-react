package jcom.cms.controllers.cp;

import jcom.cms.criteria.ProductCriteriaRepository;
import jcom.cms.entity.Category;
import jcom.cms.entity.Product;
import jcom.cms.mappingclass.ProductsList;
import jcom.cms.request.AdminBulk;
import jcom.cms.response.ProductList;
import jcom.cms.response.ProductListWithCategories;
import jcom.cms.response.ProductSingle;

import jcom.cms.services.CategoryService;
import jcom.cms.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/cp/")
public class ProductsAdminController {

    @Autowired // This means to get the bean called ProductRepository
    private ProductService service;

    @Value("${item.per.page.admin}")
    private Integer perPage;

    @Autowired
    private ProductCriteriaRepository prCriteria;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/products-list")
    public ProductListWithCategories showproductlist(HttpServletRequest request,
                                                     @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr) {

        Map<String, String[]> reqMap = new HashMap<>(request.getParameterMap());



        return new ProductListWithCategories(prCriteria.search(reqMap, PageRequest.of(pageNr, perPage)),
                                             categoryService.getByTypeIn("product"));
    }

    // if you don't like the top one use the bottom one
  /*
     @GetMapping("/products-list")
    public Page<ProductsList> showproductlist(
                          HttpServletRequest request,
                          @RequestParam(value ="userid", required=false ) String userid,
                          @RequestParam(value ="catid", required=false ) Integer catid,
                          @RequestParam(value ="search", required=false ) String search,
                          @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr) {


        if(userid !=null && !userid.isEmpty()){
            return service.findByUseridQuery(userid, PageRequest.of(pageNr, perPage));

        }else if(catid !=null && catid>0){
            return service.findByCatidQuery(catid, PageRequest.of(pageNr, perPage));

        }else if( search !=null && !search.isEmpty()){
            return service.findByTitleQuery(search,  PageRequest.of(pageNr, perPage));

        } else {
            return service.getAllProductsQuery(PageRequest.of(pageNr, perPage));

         }

    }*/


    /**
     * this part we duplicate the content from one language to other
     * @param
     * @return
     */
    @GetMapping("/product-duplicate")
    public String duplicate(HttpServletRequest request){
        String  id = request.getParameter("id");
        String  langFrom = request.getParameter("langFrom");
        String  langTo = request.getParameter("langTo");

        return  service.duplicate(id, langFrom, langTo);
    }



    @GetMapping("/product-get-single")
    public ProductSingle product_add_edit(@RequestParam(value ="id", required=false) String id ) {

        return service.getFull(id);

    }




    /**
     *  get data from post and put in database, and after redirect back
     * @param request
     * @return
     */

    @PutMapping(path="/update-product") // Map ONLY POST Requests

    public String store (HttpServletRequest request,
                          @RequestParam(value ="imgmain", required=false ) MultipartFile imgmain) {

        return service.updateProduct(request,imgmain);


    }
 



    /**
     *  Update or delete bulk
     * @param request
     * @param data
     * @return
     */
    // Map ONLY POST Requests
    @PutMapping(path="/products-action", consumes = "application/json", produces = "application/json")
    public String updateBulk (HttpServletRequest request, @RequestBody AdminBulk data) {

       try{
            if(data.getId().size()>0 && data.getAction().contains("del")){

                for (String idnr : data.getId()) {
                    service.delete(Integer.parseInt(idnr));
                }

            }else if(data.getId().size()>0 &&
                    (data.getAction().contains("1") || data.getAction().contains("2"))){

                for (String idnr : data.getId()) {
                    Product product = service.getOne(idnr) ;
                    product.setStatus(Integer.parseInt(data.getAction()));
                    service.save(product);
                }

            }else if(data.getId().size()>0 && data.getAction().contains("move")){

                for (String idnr : data.getId()) {
                    Product product = service.getOne(idnr) ;
                    product.setCatid("[\""+data.getCategoryId()+"\"]");
                    service.save(product);
                }

            }

            return "ok";

        }catch (Exception e){
           return e.getMessage();
       }

    }


    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(path="/product-remove") // Map ONLY POST Requests

    public String delete (@RequestParam(value ="id") Integer id ) {

        try{
            service.delete(id);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }


    @GetMapping(path="/product-show-hide") // Map ONLY POST Requests

    public String status (@RequestParam(value ="id") String id, @RequestParam(value ="hide") Integer hide ) {

        try{
            Product product = service.getOne(id) ;
            product.setStatus(hide);
            service.save(product);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }


}
