package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    //@Query(value = "SELECT vehicle.name, vehicle.owner, vehicle.type, warehouse.name FROM sarokh_db.vehicle as vehicle, sarokh_db.sarokh_warehouse as warehouse where vehicle.warehouse_id=warehouse.id", nativeQuery = true)

}
