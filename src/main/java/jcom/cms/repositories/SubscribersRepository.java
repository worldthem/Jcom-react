package jcom.cms.repositories;

import jcom.cms.entity.Subscribers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribersRepository extends JpaRepository<Subscribers, Integer> {
    Page<Subscribers> findByEmailContainingIgnoreCase(String search, Pageable pageable);
    Page<Subscribers> findAllByOrderByIdDesc(Pageable pageable);
    long count();

}
