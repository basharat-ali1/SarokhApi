package net.sarokh.api.dao;

import net.sarokh.api.model.entity.ShipperCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShipperCrewRepository extends JpaRepository<ShipperCrew, Integer> {
}
