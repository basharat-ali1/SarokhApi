package net.sarokh.api.dao;

import net.sarokh.api.model.entity.DeliveryCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DeliveryChargesRepository extends JpaRepository<DeliveryCharges, Integer> {

    /*
    Optional<DeliveryCharges> findByChargesType(String chargesType);

    Optional<DeliveryCharges> findByChargesTypeAndShipperId(String chargesType, Integer shipperId);
*/
    List<DeliveryCharges> findByShipperId(Integer shipperId);
   // @Query(value = "SELECT name from city WHERE country_code =:countryCode ORDER BY name", nativeQuery = true)
   // Iterable<?> findCitiesByCountryCode(String countryCode);
}
