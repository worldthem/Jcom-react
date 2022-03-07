package jcom.cms.controllers.cp;


import jcom.cms.helpers.HelpersFile;
import jcom.cms.response.AdminVars;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this controller is for settings,
 * return data like admin menu or other data
 */

@RestController
@RequestMapping("/api/v1/cp/")
public class VariablesController {

    @GetMapping("/variables")
    public AdminVars getdata() {
      return new AdminVars(HelpersFile.getAdminMenu(""));
    }

}
