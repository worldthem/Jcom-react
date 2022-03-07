package jcom.cms.services;

import jcom.cms.entity.Country;
import jcom.cms.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService{

 @Autowired
 private CountryRepository repository;


 public Iterable<Country> getAll(){
     return repository.findAll();
 }

 public String update(Country data){
     try{
          //Country row = data.getId() >0? repository.findFirstById(data.getId()) : data;
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

}
