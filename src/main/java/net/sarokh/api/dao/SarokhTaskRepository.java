package net.sarokh.api.dao;

import net.sarokh.api.model.entity.SarokhTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface SarokhTaskRepository extends JpaRepository<SarokhTask, Integer> {

    Iterable<SarokhTask> findByStatus(String status);

    @Query(value = "SELECT * FROM sarokh_task WHERE dispatch_trip_id =:tripId AND status = 'Active'", nativeQuery = true)
    Iterable<SarokhTask> getSarokhTasksByTripId(Integer tripId);

    @Query(value = "SELECT * FROM sarokh_task WHERE stop_type = 'Sarokh Point' AND status = 'Active' AND location_id = :pointId ", nativeQuery = true)
    Iterable<SarokhTask> getDealerPointSarokhTasks(Integer pointId);

    @Query(value = "SELECT * FROM sarokh_task WHERE driver_id =:driverId AND status = 'Active' ", nativeQuery = true)
    Iterable<SarokhTask> getDriverSarokhTasks(Integer driverId);

    @Query(value = "SELECT * FROM sarokh_task WHERE driver_id =:driverId AND location_id =:pointId AND stop_type = 'Sarokh Point' AND status = 'Active'", nativeQuery = true)
    Iterable<SarokhTask> findByDealerPointIdAndDriverIdAAndStatus(Integer pointId, Integer driverId);

}
