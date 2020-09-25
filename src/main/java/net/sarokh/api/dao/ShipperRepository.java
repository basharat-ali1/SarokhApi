package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
    Shipper findByUserId(Integer userId);
}
