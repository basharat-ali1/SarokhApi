package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findByUserId(Integer userId);
}
