package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> getRoleByName(String name);

    Iterable<Role> findAllByParentRole(String parentRole);
}
