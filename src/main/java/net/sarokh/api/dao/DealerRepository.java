package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DealerRepository extends JpaRepository<Dealer, Integer> {

    Dealer findByUserId(Integer userId);
}
