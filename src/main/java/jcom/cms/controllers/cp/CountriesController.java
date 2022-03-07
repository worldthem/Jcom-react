package jcom.cms.controllers.cp;

import jcom.cms.entity.Country;
import jcom.cms.request.AdminBulk;
import jcom.cms.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1/cp/")
public class CountriesController {

 @Autowired
 private CountryService service;

 @GetMapping("/get-all-countries")
 public Iterable<Country> getAll(){
  return service.getAll();
 }


 /**
  * Update Insert Category
  * @param data
  * @return
  */
 @PutMapping(path="/insert-update-country", consumes = "application/json", produces = "application/json")
 public String saveCategory(@RequestBody Country data){
   return service.update(data);
 }

 /**
  *
  * @param id
  * @return
  */
 @DeleteMapping(path="/country-remove") // Map ONLY POST Requests
 public String delete (@RequestParam(value ="id") Integer id ) {
   return service.delete(id);
 }


 @PostMapping(path="/countries-action", consumes = "application/json", produces = "application/json")
 public String updateBulk (HttpServletRequest request, @RequestBody AdminBulk data) {

  try{
    if(data.getId().size()>0 && data.getAction().contains("del")){
     for (String idnr : data.getId()) {
       service.delete(Integer.parseInt(idnr));
     }
    }
   return "ok";
  }catch (Exception e){
   return e.getMessage();
  }

 }

}
