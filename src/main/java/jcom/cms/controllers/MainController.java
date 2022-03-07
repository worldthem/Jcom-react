package jcom.cms.controllers;

import jcom.cms.helpers.Helpers;
import jcom.cms.helpers.View;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import freemarker.template.Template;


@Component
@Log4j2
public class MainController {
    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;

    private Map<String, String> settings;
    public static HashMap<String, String> allSettins = null;

    @Autowired
    private View view;


    /**
     *  Will compile html and return data in string format
     * @param templateModel
     * @param htmlpath
     * @return = html compiled
     */
    public String templateHtml(Map<String, Object> templateModel, String htmlpath){
        try{
            Template freemarkerTemplate = freemarkerConfigurer.createConfiguration()
                                           .getTemplate(Helpers.view(htmlpath, "view"));
            templateModel.put("view", view);
            templateModel.put("baseurl",Helpers.baseurl());

            return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        }catch (Exception e){
            log.error("Html template error: "+e.getMessage());
        }

        return "";
    }
}
