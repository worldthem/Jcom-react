package jcom.cms.controllers.cp;

import jcom.cms.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/cp/")

public class EmailSettingsAdminController {
    @Autowired
    private MailService mailService;

    /**
     * this part will send test mail
     * @param
     * @return
     */
    @GetMapping("/send-test-email")
    public String sendTestMail(HttpServletRequest request){
        String  to = request.getParameter("to");
        String  text = request.getParameter("text");
        return  mailService.sendmail(to,"Test Mail", text);
    }
}
