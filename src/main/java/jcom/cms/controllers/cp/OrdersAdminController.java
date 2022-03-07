package jcom.cms.controllers.cp;

import jcom.cms.entity.Orders;
import jcom.cms.services.OrdersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/cp/")
@Log4j2
public class OrdersAdminController {

    @Autowired
    private OrdersService service;

    @Value("${item.per.page.admin}")
    private Integer perPage;

    @GetMapping("/get-list-of-orders")
    public Page<Orders> getOneById(HttpServletRequest request,
                                   @RequestParam(value ="status", defaultValue = "") String status,
                                   @RequestParam(value ="search", defaultValue = "") String search,
                                   @RequestParam(value ="userid", defaultValue = "0") Integer userid,
                                   @RequestParam(value ="pageNr", defaultValue = "0") Integer pageNr
                                  ){

        return service.getList(status, search, userid, PageRequest.of(pageNr, perPage));
    }

}
