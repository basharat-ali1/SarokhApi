package net.sarokh.api.dao;

import net.sarokh.api.model.entity.ShipperTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShipperTariffRepository extends JpaRepository<ShipperTariff, Integer> {
}
