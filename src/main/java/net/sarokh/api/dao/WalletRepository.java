package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Optional<Wallet> findByUserIdAndUserType(Integer userId, String userType);

    Iterable<Wallet> findByUserTypeIdAndUserType(Integer userTypeId, String userType);

    Optional<Wallet> findByUserIdAndUserTypeAndWalletType(Integer userId, String userType, String walletType);
}