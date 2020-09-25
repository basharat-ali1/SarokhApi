package net.sarokh.api.dao;

import net.sarokh.api.model.entity.SarokhTaskConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SarokhTaskConfirmationRepository extends JpaRepository<SarokhTaskConfirmation, Integer> {

    @Query(value = "SELECT * FROM sarokh_task_confirmation WHERE driver_id =:driverId AND status = 'Active' ", nativeQuery = true)
    Optional<SarokhTaskConfirmation> getSarokhTaskConfirmationByDriverId(Integer driverId);

    @Query(value = "SELECT * FROM sarokh_task_confirmation WHERE driver_id =:driverId AND status = 'Active' ", nativeQuery = true)
    List<SarokhTaskConfirmation> checkSarokhTaskConfirmationForDriver(Integer driverId);
}
