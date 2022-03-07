package jcom.cms.services;

import jcom.cms.entity.Shipping;
import jcom.cms.repositories.CountryRepository;
import jcom.cms.repositories.ShippingRepository;
import jcom.cms.response.ShippingMethodsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShippingMethodsServices {

    @Autowired
    private ShippingRepository repository;

    @Autowired
    private CountryRepository countryRepository;

    public ShippingMethodsResponse getList(Pageable pageable){
        return new ShippingMethodsResponse(repository.findAll(pageable), countryRepository.findAll()) ;
    }


    public String update(Shipping data){
        try{
            repository.save(data);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete(Integer id){
        try{
            repository.deleteById(id);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public Shipping getOne(Integer id) {
       return repository.getOne(id);
    }

    public Shipping save(Shipping data) {
      return repository.save(data);
    }
}
