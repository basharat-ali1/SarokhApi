package net.sarokh.api.dao;

import net.sarokh.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserNameAndUserPassword(String userName, String userPassword);

    Iterable<User> findUserByUserTypeAndParentTypeId(String userType, Integer parentTypeId);

    Iterable<User> findUserByUserTypeIn(List<String> userType);

    Optional<User> findByUserType(String userType);

    Optional<User> findByContact(String contact);

    void deleteByUserId(Integer id);
}
