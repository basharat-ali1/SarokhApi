package net.sarokh.api.dao;

import net.sarokh.api.model.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {

    Iterable<PaymentTransaction> findAllByPaymentMethod(String paymentMethod);

    Iterable<PaymentTransaction> findAllByPaymentMethodAndUserId(String paymentMethod, Integer userId);
}
