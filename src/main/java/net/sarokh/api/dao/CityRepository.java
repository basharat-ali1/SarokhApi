package net.sarokh.api.dao;

import net.sarokh.api.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query(value = "SELECT name from city WHERE country_code =:countryCode ORDER BY name", nativeQuery = true)
    Iterable<?> findCitiesByCountryCode(String countryCode);
}
