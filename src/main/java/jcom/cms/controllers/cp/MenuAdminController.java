package jcom.cms.controllers.cp;

import jcom.cms.entity.Category;
import jcom.cms.entity.Pages;
import jcom.cms.entity.Settings;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.response.MenuSingleResponse;
import jcom.cms.services.CategoryService;
import jcom.cms.services.PagesService;
import jcom.cms.services.SettingsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/cp/")
@Log4j2
public class MenuAdminController {

    @Autowired
    private SettingsService service;

    @Autowired
    private PagesService pagesService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/get-menu-single")
    public MenuSingleResponse getOneById(@RequestParam(value ="id" ) Integer id){
        Map<String, String> customLinks = HelpersFile.getConfigXmlData("menulink", "customlink", "");
        List<Pages> pages = pagesService.getByTypeList("pages");
        List<Category> categories = categoryService.getAll();
        Map<String, String> typeOfCat = HelpersFile.getConfigXmlData("config", "categorieslink", "");
        return new MenuSingleResponse(service.findById(id), customLinks, pages, categories, typeOfCat);
    }

    /**
     * this part we duplicate the content from one language to other
     * @param
     * @return
     */
    @GetMapping("/menu-duplicate")
    public String duplicate(HttpServletRequest request){
        String  pageid = request.getParameter("pageid");
        String  langFrom = request.getParameter("langFrom");
        String  langTo = request.getParameter("langTo");

        return  service.duplicate(pageid, langFrom, langTo);
    }

}
