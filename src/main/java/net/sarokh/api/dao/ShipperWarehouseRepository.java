package net.sarokh.api.dao;

import net.sarokh.api.model.entity.ShipperWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShipperWarehouseRepository extends JpaRepository<ShipperWarehouse, Integer> {
    Iterable<ShipperWarehouse> findAllByShipperId(Integer id);
}
