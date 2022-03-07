package jcom.cms.controllers;

import jcom.cms.entity.Pages;
import jcom.cms.entity.Settings;
import jcom.cms.services.PagesService;
import jcom.cms.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class HomeController {
    @Autowired
    private PagesService service;

    @Autowired
    private SettingsService settingsService;

    @GetMapping(value = "/get-home-page")
    public Pages getList() { //@PathVariable String type
        return service.getHome();
    }

    @GetMapping(value = "/get-template-layout")
    public Settings getLayout(@RequestParam(value ="type" ) String type) {



        return type.compareTo("_footer") ==0 || type.compareTo("_header") ==0 ?
                     settingsService.findFirstByParam(type) : new Settings();
    }

}
