package net.sarokh.api.dao;

import net.sarokh.api.model.entity.DispatchTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DispatchTripRepository extends JpaRepository<DispatchTrip, Integer> {

    @Query(value = "SELECT * FROM dispatch_trip ORDER BY id DESC", nativeQuery = true)
    Iterable<DispatchTrip> findAllDscOrder();

    @Query(value = "SELECT * FROM dispatch_trip WHERE status=:status ORDER BY id DESC", nativeQuery = true)
    Iterable<DispatchTrip> findAllByTripStatus(String status);

    Iterable<DispatchTrip> findAllByDriverId(Integer driverId);

    @Query(value = "SELECT created_datetime, dispatch_datetime, completion_datetime, cod_collection, " +
            "(SELECT count(*) from sarokh_task where dispatch_trip_id = trip.id ) as 'stops', id, pickup_shipments, delivery_shipments " +
            "FROM dispatch_trip trip " +
            "WHERE driver_id =1 AND status='Active'", nativeQuery = true)
    Iterable<?> findDriverTrips(Integer driverId);

    List<DispatchTrip> findAllByDriverIdAndTripStatus(Integer driverId, String status);

    @Query(value = "SELECT * FROM dispatch_trip WHERE id =:id AND status='Active'", nativeQuery = true)
    Optional<DispatchTrip> findAllByIdAndTripStatus(Integer id);
}
