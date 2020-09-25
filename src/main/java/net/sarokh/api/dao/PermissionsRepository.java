package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PermissionsRepository extends JpaRepository<Permission, Integer> {
}
