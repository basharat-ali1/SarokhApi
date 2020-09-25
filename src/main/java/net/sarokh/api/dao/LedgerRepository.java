package net.sarokh.api.dao;

import net.sarokh.api.model.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface LedgerRepository extends JpaRepository<Ledger, Integer> {

    @Query(value = "SELECT * FROM ledger ORDER BY id DESC", nativeQuery = true)
    Iterable<Ledger> findAllLedgersDescOrder();

    @Query(value = "SELECT ledger.id, shipper.first_name, shipper.last_name, ledger.transaction_type, ledger.created_datetime, " +
            "ledger.total_amount, ledger.shipments_id_list FROM ledger ledger, shipper shipper where shipper.id = ledger.bill_to " +
            "AND ledger.user_type = 'Shipper'", nativeQuery = true)
    Iterable<?> findShipperBilling();

    @Query(value = "SELECT * FROM ledger WHERE shipper_id=:shipperId ORDER BY id DESC", nativeQuery = true)
    Iterable<Ledger> findAllByShipperId(Integer shipperId);

    Iterable<Ledger> findAllByPaymentStatus(String paymentStatus);

    Iterable<Ledger> findAllByPaymentStatusAndBillToAndUserType(String paymentStatus, Integer billTo, String userType);

    //Iterable<Ledger> findAllByLedgerName(String ledgerName);

   // @Query(value = "SELECT * FROM ledger WHERE ledger_name='Shipper COD' OR ledger_name='Shipper Delivery Charges'", nativeQuery = true)
    //Iterable<Ledger> getShipperBilling();
}
