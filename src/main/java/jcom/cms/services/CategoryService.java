package jcom.cms.services;

import jcom.cms.entity.Category;
import jcom.cms.helpers.Helpers;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.repositories.CategoryRepository;
import jcom.cms.response.CategoriesSingle;
import jcom.cms.response.InsertCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    /**
     * Get categories by Type
     * @param type
     * @return
     */
    public List<Category> getByType(String type){
       return repository.findByTypeIgnoreCaseOrderByCatidAscParentAsc(type);
    }

    /**
     * Get categories by Types
     * @param type
     * @return
     */
    public List<Category> getByTypeIn(String type){
        Map<String, String> cat = HelpersFile.getConfigXmlData("categorieslink",type, "");

        Collection<String> catTypeIn = new ArrayList<>();
        cat.forEach((k,v)->catTypeIn.add(k));

        return repository.findByTypeIn(catTypeIn);
    }

    /**
     * Get all categories
     * @return
     */
    public List<Category> getAll(){
        return repository.findAll();
    }

    /**
     * insert Category
     * @param data
     * @return
     */
    public InsertCategory insertShort(Map<String, String> data) {

       try {
           Category category = new Category();
           category.setType(data.get("type"));
           category.setTitle(data.get("title"));
           category.setMetad(data.get("title"));
           category.setMetak(data.get("title"));
           category.setParent(Integer.parseInt(data.get("parent")));
           category.setCpu(Helpers.slug(data.get("cpu")));

           Category cat = repository.save(category);

           return new InsertCategory("ok", cat) ;
       }catch (Exception e){
           return new InsertCategory(e.getMessage(),null);
       }

    }

    /**
     * Delete Category
     * @param id
     */
    public void delete(String id) {
        repository.deleteById(Integer.parseInt(id));
    }

    /**
     * get Category by id
     * @param id
     * @return
     */
    public Category getOne(Integer id){
        return repository.getOne(id);
     }

    /**
     * get Category by id
     * @param id
     * @return
     */
    public CategoriesSingle getSingle(Integer id, String type){

        Category category = id >0? repository.findByCatid(id) : null;

        return new CategoriesSingle(repository.findByTypeIgnoreCaseOrderByCatidAscParentAsc(type), category);
    }

    /**
     * Update Insert Category
     * @param category
     * @return
     */
    public Category save(Category category){
        return repository.save(category);
    }

    /**
     * Update Insert Category
     * @param data
     * @return
     */
    public Category updateInsert(Category data){
        Category cat = data.getCatid() !=null && data.getCatid() > 0 ? repository.findByCatid(data.getCatid()) : null ;
        cat = cat != null ? cat : new Category();

        cat.setTitle(Helpers.saveLang(data.getTitle(),cat.getTitle(), data.getLang()));
        cat.setMetad(Helpers.saveLang(data.getMetad(),cat.getMetad(), data.getLang()));
        cat.setMetak(Helpers.saveLang(data.getMetak(),cat.getMetak(), data.getLang()));
        cat.setText(Helpers.saveLang(data.getText(),cat.getText(), data.getLang()));
        cat.setCpu(data.getCpu());
        cat.setType(data.getType());
        cat.setUrl(data.getUrl());
        cat.setParent(data.getParent());

       return save(cat);
    }

}
