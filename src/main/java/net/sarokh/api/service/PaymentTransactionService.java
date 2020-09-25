package net.sarokh.api.service;

import net.sarokh.api.dao.PaymentTransactionRepository;
import net.sarokh.api.dao.UserRepository;
import net.sarokh.api.model.entity.PaymentTransaction;
import net.sarokh.api.model.entity.User;
import net.sarokh.api.model.entity.Wallet;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentTransactionService {

    @Autowired
    private PaymentTransactionRepository repository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public ApiResponse addPaymentTransaction (PaymentTransaction paymentTransaction){

        if (null != paymentTransaction.getUserId()){
            Optional<User> user = userRepository.findById(paymentTransaction.getUserId());
            /*Optional<Wallet> wallet = walletService.findByUserIdAndUserType(user.get().getUserId(), user.get().getUserType());

            if (wallet.isPresent()){
                paymentTransaction.setWalletId(wallet.get().getWalletId());
                PaymentTransaction transaction = repository.save(paymentTransaction);

                walletService.updateWalletForTransaction(wallet.get(), paymentTransaction);
            */
                return ApiResponse.builder()
                        .data(null)
                        .status(HttpStatus.OK.value())
                        .message(ApplicationMessagesUtil.SUCCESS)
                        .build();
            //}
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ApplicationMessagesUtil.INVALID_TRANSACTION_NUMBER)
                .build();
    }

    public Optional<PaymentTransaction> getPaymentTransactionById(Integer id){
        return repository.findById(id);
    }

    public Iterable<PaymentTransaction> getPaymentTransactionsList() {
        return repository.findAll();
    }

    public Iterable<PaymentTransaction> getListByPaymentMethod(String paymentMethod) {
        return repository.findAllByPaymentMethod(paymentMethod);
    }

    public PaymentTransaction updatePaymentTransaction (PaymentTransaction paymentTransaction){
        return repository.save(paymentTransaction);
    }

    public ApiResponse deletePaymentTransaction (Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data(id + "successfully deleted")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }
}
