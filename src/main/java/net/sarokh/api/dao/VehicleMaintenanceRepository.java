package net.sarokh.api.dao;

import net.sarokh.api.model.entity.VehicleMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface VehicleMaintenanceRepository extends JpaRepository<VehicleMaintenance, Integer> {
}
