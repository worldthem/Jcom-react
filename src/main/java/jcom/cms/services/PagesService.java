package jcom.cms.services;

import jcom.cms.entity.Pages;
import jcom.cms.entity.Settings;
import jcom.cms.helpers.Helpers;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.helpers.HelpersJson;
import jcom.cms.pojo.MainOptions;
import jcom.cms.repositories.CategoryRepository;
import jcom.cms.repositories.PagesRepository;
import jcom.cms.response.PageFullResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PagesService {

    // import repository
    @Autowired
    private PagesRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SettingsService settingsService;


    public Pages getHome(){
        MainOptions opt =  settingsService.getMainOpt() ;
        if(opt.getPageHome()>0)
            return repository.findByPageid(opt.getPageHome());

        return null;
    }


    /**
     * get by type
     * @param type
     * @param pageable
     * @return
     */
    public Page<Pages> getByType(String type, Pageable pageable){
        return repository.findByTypeOrderByPageidDesc(type, pageable);
    }

    /**
     * get by type
     * @param type
     * @return
     */
    public List<Pages> getByTypeList(String type ){
        return repository.findByTypeOrderByPageidDesc(type);
    }

    /**
     * get pages by type and categoryid
     * @param type
     * @param catId
     * @param pageable
     * @return
     */
    public Page<Pages> getByTypeAndCatid(String type, String catId, Pageable pageable){
        return repository.findByTypeAndCategories_CatidOrderByPageidDesc(type, Integer.parseInt(catId), pageable);
    }

    /**
     * will search by title and type
     * @param type
     * @param search
     * @param pageable
     * @return
     */
    public Page<Pages> search(String type, String search, Pageable pageable){
        return repository.findByTypeAndTitleContainsIgnoreCaseOrderByPageidDesc(type, search, pageable);
    }

    /**
     * delete page by id
     * @param id
     */
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Get on page by id
     * @param id
     * @return
     */
    public Pages getOne(Integer id) {
       return repository.findByPageid(id);
    }


    /**
     * Get on page by id
     * @param id
     * @return
     */
    public PageFullResponse getOneFull(Integer id, String type) {
        Map<String, String> cat = HelpersFile.getConfigXmlData("categorieslink",type, "");

        Collection<String> catTypeIn = new ArrayList<>();
        cat.forEach((k,v)->catTypeIn.add(k));

        return new PageFullResponse( id > 0 ? repository.findByPageid(id) : new Pages(),
                   categoryRepository.findByTypeIn(catTypeIn),
                   cat) ;
    }

    /**
     *  Update or insert the page
      * @param pages
     * @return
     */
    public Pages save(Pages pages) {
        return repository.save(pages);
    }

    /**
     * Update Insert Page
     * @param data
     * @return
     */
    public Pages updateInsert(Pages data){
        Pages row = data.getPageid() > 0 ? repository.findByPageid(data.getPageid()) : null ;
        row = row != null ? row : new Pages();

        row.setTitle(Helpers.saveLang(data.getTitle(),row.getTitle(), data.getLang()));
        row.setMetad(Helpers.saveLang(data.getMetad(),row.getMetad(), data.getLang()));
        row.setMetak(Helpers.saveLang(data.getMetak(),row.getMetak(), data.getLang()));
        row.setText(Helpers.saveLang(data.getText(),row.getText(), data.getLang()));
        row.setCpu(data.getCpu());
        row.setType(data.getType());
        //row.setCatid(data.getCatid());
        row.setSort(data.getSort());
        row.setSubject(data.getSubject());
        row.setStyle(data.getStyle());
        row.setCss(data.getCss());
        row.setCategories(data.getCategories());


        return save(row);
    }

    public String duplicate(String pageid, String langFrom, String langTo) {
        try{
        Pages row  =repository.findByPageid(Integer.parseInt(pageid));
         if(row !=null){

             row.setTitle(Helpers.duplicateContent(row.getTitle(), langFrom, langTo));
             row.setMetad(Helpers.duplicateContent(row.getMetad(), langFrom, langTo));
             row.setMetak(Helpers.duplicateContent(row.getMetak(), langFrom, langTo));
             row.setText(Helpers.duplicateContent(row.getText(), langFrom, langTo));

             save(row);
          }

         return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }


    public Map<String, List<String>> getReadyBlocks(){
         Settings settings = settingsService.findFirstByParam("_page_builder_");
        try {
             return HelpersJson.readyBlocks(settings.getValue1());
        }catch (Exception e){
             return  new HashMap<>();
        }
    }

    public Map<String, List<String>> updateReadyBlocks(){

       try {
           String json = HelpersJson.jsonGetRequest("https://ecommercecms.org/json/bulderblocks");
            Settings settings = settingsService.findFirstByParam("_page_builder_");
            settings= settings== null? new Settings() : settings;
            settings.setValue1(json);
            settings.setParam("_page_builder_");
            settingsService.save(settings);
           return HelpersJson.readyBlocks(json);
       }catch (Exception e){
            return new HashMap<>();
       }
    }

    public Pages getByDirection(String direction) {
        return repository.findFirstByDirection(direction);
    }
}
