package jcom.cms.services;

import jcom.cms.entity.Pages;
import jcom.cms.entity.Role;
import jcom.cms.entity.Users;

import jcom.cms.repositories.RoleRepository;
import jcom.cms.repositories.UsersRepository;
import jcom.cms.response.UsersResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Log4j2
public class UsersService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Page<Users> getList(String search,Pageable pageable){

        if(!search.isEmpty()){
           return repository.findByEmailContainingOrFirstNameContainingOrLastNameContainingIgnoreCase(search,search,search, pageable);

        }else{
            return repository.findAllByOrderByUseridDesc(pageable);
        }

    }

    public String save(Users users) {
        try{
           repository.save(users);
            return "ok";
        }catch (Exception e){
            log.error("Save user: "+e.getMessage());
            return e.getMessage();
        }
    }

    /**
     *  Get user by id
     * @param users
     * @return
     */
    public String insertUpdate(Users users) {
        try{

            if(users.getPassword() != null && !users.getPassword().isEmpty())
                users.setPassword(encoder.encode(users.getPassword()));

           if(users.getUserid() > 0 && (  users.getPassword() == null || users.getPassword().isEmpty())){
             Users one = repository.findByUserid(users.getUserid());
             users.setPassword(one.getPassword());
            }

           /*
            Collection<Role> roles = users.getRoles();
            Collection<Role> newRoles = new ArrayList<>();

           if(roles.size() > 0){
             Role role = roles.stream().findFirst().get();
             String name = role.getName();
             if(name.contains("::")){
                 String[] split = name.split("::");
                 newRoles.add(new Role(Long.parseLong(split[0]), split[1]));
             }
           }
            users.setRoles(newRoles);
           */

            repository.save(users);
            return "ok";
        }catch (Exception e){
            log.error("Save user: "+e.getMessage());
            return e.getMessage();
        }
     }

    /**
     * delete user by id
     * @param id
     */
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Get on user by id
     * @param id
     * @return
     */

    public Users getOne(Integer id) {
        return repository.findByUserid(id);
    }

    /**
     * Get on user by id
     * @param id
     * @return
     */

    public UsersResponse getOneWithRoles(Integer id) {
         return new UsersResponse(id>0? repository.findByUserid(id): new Users(),
                                  roleRepository.findAll()) ;
    }
}
