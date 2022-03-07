package jcom.cms.controllers.services;

import jcom.cms.entity.Subscribers;
import jcom.cms.repositories.SubscribersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribersService {

    @Autowired
     private SubscribersRepository repository;

    public Page<Subscribers> getList(String search, PageRequest pageable) {
        if(!search.isEmpty()){
            return repository.findByEmailContainingIgnoreCase(search, pageable);

        }else{
            return repository.findAllByOrderByIdDesc(pageable);
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<Subscribers> findAll() {
        return repository.findAll();
    }
}
