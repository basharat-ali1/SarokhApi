package net.sarokh.api.service;

import net.sarokh.api.dao.WalletRepository;
import net.sarokh.api.model.GetBillDetailDTO;
import net.sarokh.api.model.entity.PaymentTransaction;
import net.sarokh.api.model.entity.Wallet;
import net.sarokh.api.model.enums.TransactionTypeEnum;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.model.enums.WalletTypeEnum;
import net.sarokh.api.model.mobile.WalletDealerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;

    public Wallet addWallet (Wallet wallet){
        return repository.save(wallet);
    }

    public List<Wallet> addWallets(List<Wallet> wallets){
        return repository.saveAll(wallets);
    }

    public Wallet createWalletObject (String description, String walletType, Integer userTypeId, String userType, Integer userId, String walletHolder){
        Wallet wallet = new Wallet();

        wallet.setDescription(description);
        wallet.setWalletType(walletType);
        wallet.setCurrentBalance(0.0);
        wallet.setPayable(0.0);
        wallet.setReceivable(0.0);
        wallet.setUserTypeId(userTypeId);
        wallet.setUserType(userType);
        wallet.setUserId(userId);
        wallet.setWalletHolder(walletHolder);

        return wallet;
    }

    public Optional<Wallet> getWalletById(Integer id){
        return repository.findById(id);
    }

    public Iterable<Wallet> getWalletsList() {
        return repository.findAll();
    }

    public Iterable<Wallet> getUserWalletsList(GetBillDetailDTO billToDetailDTO) {
        return repository.findByUserTypeIdAndUserType(billToDetailDTO.getUserId(), billToDetailDTO.getUserType());
    }

    public Optional<Wallet> getDriverCollectionWallet(Integer userId) {
        return repository.findByUserIdAndUserTypeAndWalletType(userId, UserRolesEnum.Driver.name(), WalletTypeEnum.DriverCollection.getWalletType());
    }

    public Optional<Wallet> getDealerPointCollectionWallet(Integer userId) {
        return repository.findByUserIdAndUserTypeAndWalletType(userId, UserRolesEnum.DealerPoint.name(), WalletTypeEnum.DealerCollection.getWalletType());
    }

    public Optional<Wallet> getDriverCompensationWallet(Integer userId) {
        return repository.findByUserIdAndUserTypeAndWalletType(userId, UserRolesEnum.Driver.name(), WalletTypeEnum.DriverCompensation.getWalletType());
    }

    public Optional<Wallet> getDealerPointCompensationWallet(Integer userId) {
        return repository.findByUserIdAndUserTypeAndWalletType(userId, UserRolesEnum.DealerPoint.name(), WalletTypeEnum.DealerCompensation.getWalletType());
    }

    public Wallet updateWallet (Wallet wallet){
        return repository.save(wallet);
    }

    public void deleteWallet (Integer id){
        repository.deleteById(id);
    }

    public Optional<Wallet> findByUserIdAndUserType(Integer userId, String userType){
        return repository.findByUserIdAndUserType(userId, userType);
    }

    public Wallet updateWalletForTransaction (Wallet wallet, PaymentTransaction paymentTransaction){
        if (null != paymentTransaction.getTransactionType()){
            if (paymentTransaction.getTransactionType().equals(TransactionTypeEnum.Credit.name())){
                wallet.setCurrentBalance(null != wallet.getCurrentBalance() ? wallet.getCurrentBalance()
                        + paymentTransaction.getAmountPaid() : paymentTransaction.getAmountPaid());
                wallet.setReceivable((null != wallet.getReceivable() && wallet.getReceivable() > 0) ? wallet.getReceivable()
                        - paymentTransaction.getAmountPaid() : wallet.getReceivable());
            } else if (paymentTransaction.getTransactionType().equals(TransactionTypeEnum.Debit.name())){
                wallet.setCurrentBalance(null != wallet.getCurrentBalance() ? wallet.getCurrentBalance()
                        - paymentTransaction.getAmountPaid() : 0 - paymentTransaction.getAmountPaid());
                wallet.setPayable((null != wallet.getPayable() && wallet.getPayable() > 0) ? wallet.getPayable()
                        - paymentTransaction.getAmountPaid() : wallet.getPayable());
            }
        }
        return repository.save(wallet);
    }

    public WalletDealerDTO getDealerWalletDetail(){
        return WalletDealerDTO.builder()
                .codCollected(0.0)
                .payToSarokhBalance(0.0)
                .sarokhPay(0.0)
                .build();
    }
}
