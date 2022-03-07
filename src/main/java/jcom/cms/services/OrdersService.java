package jcom.cms.services;

import jcom.cms.entity.Orders;
import jcom.cms.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository repository;


    public Page<Orders> getList(String status, String search, Integer userid, PageRequest of) {

        if(!status.isEmpty()) {
            return repository.findByStatusOrderByOrdersidDesc(status, of);
        }else if(!search.isEmpty()){
            return repository.findByStatusContainingOrShippingContainingOrderByOrdersidDesc(search,search, of );
        }else if(userid>0){
            return repository.findByUseridOrderByOrdersidDesc(userid, of);
        }else{
            return repository.findAll(of);
        }

    }
}
