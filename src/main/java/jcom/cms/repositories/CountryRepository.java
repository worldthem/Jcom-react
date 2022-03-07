package jcom.cms.repositories;

import jcom.cms.entity.Category;
import jcom.cms.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
    Country findFirstById(Integer id);

}
