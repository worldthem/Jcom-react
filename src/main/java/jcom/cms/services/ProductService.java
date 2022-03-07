package jcom.cms.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jcom.cms.criteria.ProductCriteriaRepository;
import jcom.cms.entity.*;
import jcom.cms.helpers.Helpers;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.mappingclass.ProductData;
import jcom.cms.mappingclass.ProductsList;
import jcom.cms.repositories.*;
import jcom.cms.response.ProductSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCriteriaRepository productCriteriaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Float maxPrice = 1000000000000.0f;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private MediaRepositorey mediaRepositorey;

    @Autowired
    private CategoryService categoryService;



    public Page<ProductsList> getAllProductsQuery(Pageable pageable){
        return productRepository.getAllProducts(pageable);
    }

    public Page<ProductsList> findByCatidQuery(Integer catid, Pageable pageable){
        return productRepository.findByCatid(catid, pageable);
    }

    public Page<ProductsList> findByUseridQuery(String userid,Pageable pageable){
        return productRepository.findByUserid(Integer.parseInt(userid), pageable);
    }

    public Page<ProductsList> findByTitleQuery(String title, Pageable pageable){
        return productRepository.findByTitle(title, pageable);
    }



    public Page<ProductData> getAllProducts1(Pageable pageable){
        return productRepository.getAllProducts1(pageable);
    }

    public Product findByProductid(Integer id){
        return productRepository.findByProductid(id);
    }
    public Product getByCpu(String cpu) {
        return productRepository.findFirstByCpu(cpu) ;
    }

    public Page<Product> searchByTitle(String title, Pageable pageable) {
        return productRepository.findByTitleContaining(title, pageable) ;
    }


    public List<Product> findByCatidList(String catid, Pageable pageable) {
        return productRepository.findByCatidContainingOrderByProductidAsc(catid, pageable);
    }

    public Page<Product> findByUsersid(Integer uid, Pageable pageable) {
        return productRepository.findByUseridOrderByProductidDesc(uid, pageable);
    }
    public Page<Product> search(String title, String descr, String text, String attr, Pageable pageable) {
        return productRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTextContainingIgnoreCaseOrAttrContainingIgnoreCaseOrderByProductidDesc(title,descr,text,attr,pageable);
    }

    public Page<Product> searchByCatAndWord(String cat, String title, Pageable pageable) {
        return productRepository.findByCatidContainingAndTitleContainingAllIgnoreCaseOrderByProductidDesc(cat,title,pageable);
    }

    public Page<Product> findByCatid(String catid, Pageable pageable ) {
        return productRepository.findByCategories_CatidOrderByProductidDesc(Integer.parseInt(catid), pageable);
    }

    public Page<Product> findByCatid(String catid, Pageable pageable, String pricesort, String min, String max) {
        Float minim =min == null || min.isEmpty()? 0.0f : Float.parseFloat(min);
        Float maxim = max == null || max.isEmpty()? 0.0f : Float.parseFloat(max);
        maxim = minim>0 && maxim==0 ? maxPrice : maxim;

        if(pricesort.isEmpty() ){
            if(minim > 0 || maxim > 0){
                return productRepository.findByCatidContainingAndPriceBetweenOrderByProductidDesc(catid,minim, maxim, pageable);
            }else{ // we may go with between but when you will have many records in the table this will influence on speed so decide to go with second if
                return productRepository.findByCategories_CatidOrderByProductidDesc(Integer.parseInt(catid), pageable);
            }
        }else{
            if(minim > 0 || maxim > 0){
                return pricesort.contains("desc") ?
                        productRepository.findByCatidContainingAndPriceBetweenOrderByPriceDesc(catid,minim, maxim, pageable) :
                        productRepository.findByCatidContainingAndPriceBetweenOrderByPriceAsc(catid,minim, maxim, pageable);
            }else{ // we may go with between but when you will have many records in the table this will influence on speed so decide to go with second if
                return pricesort.contains("desc") ?
                        productRepository.findByCatidContainingOrderByPriceDesc(catid, pageable) :
                        productRepository.findByCatidContainingOrderByPriceAsc(catid,  pageable);
            }
        }

    }

    public Page<Product> getByCatid(String catid, Pageable pageable ) {
        return  productRepository.findByCatidContainingOrderByPriceDesc(catid, pageable);
    }



    public Page<Product> search(String title, String descr, String text, String attr, Pageable pageable, String pricesort, String min, String max ) {
        Float minim =min == null || min.isEmpty()? 0.0f : Float.parseFloat(min);
        Float maxim = max == null || max.isEmpty()? 0.0f : Float.parseFloat(max);
        maxim = minim>0 && maxim==0 ? maxPrice : maxim;

        if(pricesort.isEmpty() ){
            if(minim > 0 || maxim > 0){
                return productRepository.findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingAndPriceBetweenOrderByProductidDesc(title,descr,text,attr, minim, maxim, pageable);
            }else{ // we may go with between but when you will have many records in the table this will influence on speed so decide to go with second if
                return productRepository.findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingOrderByProductidDesc(title,descr,text,attr, pageable);
            }

        }else{
            if(minim>0 || maxim > 0){
                return pricesort.contains("desc") ?
                        productRepository.findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingAndPriceBetweenOrderByPriceDesc(title,descr,text,attr,minim, maxim,pageable) :
                        productRepository.findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingAndPriceBetweenOrderByPriceAsc(title,descr,text,attr,minim, maxim,pageable) ;

            }else{ // we may go with between but when you will have many records in the table this will influence on speed so decide to go with second if
                return pricesort.contains("desc") ?
                        productRepository.findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingOrderByPriceDesc(title,descr,text,attr,pageable) :
                        productRepository.findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingOrderByPriceAsc(title,descr,text,attr,pageable) ;
            }
        }
    }


    //Page<Product> findAll(Pageable pageable);
    public long count() {
        return productRepository.count();
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable){
        return productRepository.findAllByOrderByProductidDesc(pageable);
    }

    public Page<Product> getAll(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public void delete(Integer id){
         productRepository.deleteById(id);
    }

    public Product getOne(String id){
        return productRepository.getOne(Integer.parseInt(id));
    }


    /**
     * small helper
     * @param request
     * @param name
     * @param existing
     * @return
     */
    private String upd(HttpServletRequest request, String name, String existing){

        return Helpers.saveLang(request.getParameter(name),
                existing, request.getParameter("lang"));
    }


    /**
     * update insert product id the single product
     * @param request
     * @param imgmain
     * @return
     */
    public String updateProduct(HttpServletRequest request, MultipartFile imgmain){
        Product product = new Product();
        String typeInsert  = request.getParameter("typeInsert");
        Integer productid  = Integer.parseInt(request.getParameter("productid"));

        Float price = request.getParameter("price") == null ? 0f:
                     Float.parseFloat(request.getParameter("price"));
        Float salePrice = request.getParameter("salePrice") == null ? 0f:
                Float.parseFloat(request.getParameter("salePrice"));

        Float weight  = request.getParameter("weight") == null ? 0f :
                            Float.parseFloat(request.getParameter("weight"));
        Integer qtu =   request.getParameter("qtu") == null? 0 :
                              Integer.parseInt(request.getParameter("qtu"));

        try{
            if(!typeInsert.contains("new"))
             product =   findByProductid(productid);

        }catch (Exception e){}

        String message = "ok";
        try {
            // the process of inserted product
            product.setAttr(request.getParameter("attr"));
            product.setTitle(upd(request, "title", product.getTitle()));
            product.setCpu(request.getParameter("cpu"));
            product.setMetad(upd(request, "metad", product.getMetad()));
            product.setMetak(upd(request, "metak", product.getMetak()));
            product.setDescription(upd(request, "description", product.getDescription()));
            product.setText(upd(request, "text", product.getText()));

            product.setStock(request.getParameter("stock"));
            product.setPrice(price);
            product.setSalePrice(salePrice);
            product.setSku(request.getParameter("sku"));
            product.setQtu(qtu);
            product.setWeight(weight);

            product.setOptionsdata(Helpers.saveLang(request.getParameter("cpu_store"),
                    product.getOptionsdata(), "cpu_store"));

           String categories = request.getParameter("categories");
            if(categories !=null && !categories.isEmpty()){
                 try {
                     Type type = new TypeToken<List<Category>>(){}.getType();
                     List<Category> cats= (new Gson()).fromJson(categories, type);
                     product.setCategories(cats);
                }catch (Exception e){}

            }

            //product.setCategories();
            //product.setCatid(request.getParameter("catid"));

            // upload main image
            if (imgmain != null) {
                product.setImage(storageService.uploadSingleImg(imgmain, ""));
            }

           Product pr = save(product);
            //when is inserted new product is generated a random id so when upload media that random id
            // is in productid so when insert product we get that inserted product.producti and replace it
            // in media.productid
            if(typeInsert.contains("new")){
                List<Media> media = mediaRepositorey.findByProductid(productid);

                List<Media> updateMedia = new ArrayList<>();
                media.forEach(media1 ->{media1.setProductid(pr.getProductid()); updateMedia.add(media1); });
                 mediaRepositorey.saveAll(updateMedia);
            }


        }catch (Exception e){
            System.out.println("Error insert or update product: "+e.getMessage());
            message = e.getMessage();
        }


          // update suggestion
        String suggestionsJson =  request.getParameter("suggestions");
        try {
            Type type = new TypeToken<Map<String, Map<String, Map<String,String>>>>(){}.getType();
            Map<String, Map<String, Map<String,String>>> mapSugestion = !suggestionsJson.isEmpty() ? (new Gson()).fromJson(suggestionsJson, type) : new HashMap<>() ;


             Gson gson = new GsonBuilder().disableHtmlEscaping().create();

             List<Settings> settingsArr = new ArrayList<>();

             for(Map.Entry<String, Map<String, Map<String,String>>> entry: mapSugestion.entrySet()){
                Settings settings = settingsRepository.findFirstByParam(entry.getKey());
                settings = settings == null ? new Settings() :settings;
                settings.setParam(entry.getKey());
                settings.setValue1(gson.toJson(entry.getValue()));
                settings.setAutoload("yes");
                settingsArr.add(settings);
              }

             settingsRepository.saveAll(settingsArr);

        } catch (Throwable ignored) { }


        return message;
    }


    /**
     * get Single product
     * @param id
     * @return
     */
    public ProductSingle getFull(String id){

          Settings attr = settingsRepository.findFirstByParam("_attributes");

          Map<String,Map<String, Map<String,String>>> suggestions = new HashMap<>();

          if(attr.getAttributes().size() > 0) {
             List<String> params = new ArrayList<>();
             attr.getAttributes().forEach((k,v)->params.add(k));
              List<Settings> settingsSuggestions =  settingsRepository.findByParamInOrderByIdDesc(params);

                for(Settings settings : settingsSuggestions){
                  try {
                      Type type = new TypeToken<Map<String, Map<String,String>>>(){}.getType();
                      Map<String, Map<String,String>> val = (new Gson()).fromJson(settings.getValue1(), type);
                      suggestions.put(settings.getParam(), val);
                  }catch (Exception e){}
                }

          }

          Product product = id.contains("new") ?  new Product() :
                                            productRepository.getOne(Integer.parseInt(id));
          if(id.contains("new")){
              int random = (int )(Math.random() * 500000000 + 510000000);
              product.setProductid(random);
              product.setAttr("{}");
           }


          Map<String, String> catSettings = HelpersFile.getConfigXmlData("categorieslink","product", "");

          Collection<String> catTypeIn = new ArrayList<>();
          catSettings.forEach((k,v)->catTypeIn.add(k));


         return new ProductSingle(product,
                                  attr.getAttributes(),
                                  suggestions,
                                  categoryRepository.findByTypeIn(catTypeIn),
                                  catSettings
                                 );
    }

    public Page<Product> getProducts(Map<String, String[]> map, Pageable page){
        return null;//productCriteriaRepository.search(map, page);
    }


    public String duplicate(String  id, String langFrom, String langTo) {

        try{
            Product row  = productRepository.findByProductid(Integer.parseInt(id));
            if(row !=null){

                row.setTitle(Helpers.duplicateContent(row.getTitle(), langFrom, langTo));
                row.setMetad(Helpers.duplicateContent(row.getMetad(), langFrom, langTo));
                row.setMetak(Helpers.duplicateContent(row.getMetak(), langFrom, langTo));
                row.setText(Helpers.duplicateContent(row.getText(), langFrom, langTo));
                row.setDescription(Helpers.duplicateContent(row.getDescription(), langFrom, langTo));

                save(row);
            }

            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
