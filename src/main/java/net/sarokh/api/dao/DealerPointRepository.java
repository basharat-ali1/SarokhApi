package net.sarokh.api.dao;

import net.sarokh.api.model.entity.DealerPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface DealerPointRepository extends JpaRepository<DealerPoint, Integer> {
    Optional<DealerPoint> findByDealerId(Integer dealerId);

    Optional<DealerPoint> findByDealerPointName(String name);

    Optional<DealerPoint> findByUserId(Integer userId);

    @Query(value = "SELECT operator_name FROM dealer_point where id =:pointId", nativeQuery = true)
    Object getConcernedPersonNameByPointId(Integer pointId);

    @Query(value = "SELECT dealerpoint.id, wallet.wallet_holder, wallet.current_balance, dealerpoint.address FROM wallet wallet, dealer_point " +
            "dealerpoint where wallet.user_type='DealerPoint' AND wallet.user_type_id=dealerpoint.id AND wallet.wallet_type = 'Dealer Collection'", nativeQuery = true)
    Iterable<?> findAllDealerPointsCollectionWallets();
}
