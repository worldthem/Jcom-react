package jcom.cms.repositories;

import jcom.cms.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    //List<Settings> findByParamAndValue2(String param, String Value2);
    Settings findFirstByParam(String param );

    List<Settings> findByParamOrderByIdDesc(String param );
    List<Settings> findByParamInOrderByIdDesc(List<String> params);

    Settings findByParamAndValue2(String param, String Value2);
    Settings getOne(Integer id);

    List<Settings> findByAutoload(String autoload);

    void deleteByParam(String param);

    Settings findFirstById(Integer id);
}