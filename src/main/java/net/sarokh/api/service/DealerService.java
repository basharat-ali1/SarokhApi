package net.sarokh.api.service;

import net.sarokh.api.dao.BankAccountRepository;
import net.sarokh.api.dao.DealerPointRepository;
import net.sarokh.api.dao.DealerRepository;
import net.sarokh.api.dao.UserRepository;
import net.sarokh.api.model.dealer.DealerCODReturnDTO;
import net.sarokh.api.model.dealer.DealerInventoryDTO;
import net.sarokh.api.model.dealer.DealerServiceChargesDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.model.dealer.DealerFormDTO;
import net.sarokh.api.model.enums.WalletTypeEnum;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private DealerPointService dealerPointService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DealerPointRepository dealerPointRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private RoleService roleService;

    public ApiResponse addDealer(DealerFormDTO dealerForm){

        Dealer dealer = converFormDataToDealer(dealerForm);

        dealerRepository.save(dealer);

        createDealerWallets(dealer);

        return ApiResponse.builder()
                .data(dealer)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DEALER_SUCCESSFULLY_CREATED)
                .build();
    }

    private void createDealerWallets(Dealer dealer){
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(walletService.createWalletObject("Dealer Collection Wallet", WalletTypeEnum.DealerCollection.getWalletType(),
                dealer.getId(), UserRolesEnum.Dealer.name(), dealer.getUserId(), dealer.getCompanyName()));
        wallets.add(walletService.createWalletObject("Dealer Compensation Wallet", WalletTypeEnum.DealerCompensation.getWalletType(),
                dealer.getId(), UserRolesEnum.Dealer.name(), dealer.getUserId(), dealer.getCompanyName()));
        walletService.addWallets(wallets);
    }

    public Optional<Dealer> getDealerById(Integer id){
        return dealerRepository.findById(id);
    }

    public ApiResponse getDealersList(){
        Iterable<Dealer> list = dealerRepository.findAll();

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();

    }

    public ApiResponse updateDealer(DealerFormDTO dealerForm){
        Dealer dealer = converFormDataToDealer(dealerForm);

        dealerRepository.save(dealer);

        return ApiResponse.builder()
                .data(dealer)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DEALER_SUCCESSFULLY_UPDATED)
                .build();
    }

    public ApiResponse deleteDealer(Integer id){

        if (null != id){
            Optional<Dealer> dealer = dealerRepository.findById(id);

            if (dealer.isPresent()){
                dealerRepository.delete(dealer.get());

                return ApiResponse.builder()
                        .data("Dealer Id=" + id + " Successfully deleted.")
                        .message(ApplicationMessagesUtil.SUCCESS)
                        .status(HttpStatus.OK.value())
                        .build();
            }
        }
        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.UNABLE_TO_DELETE)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public Dealer findByUserId(Integer userId){
        return dealerRepository.findByUserId(userId);
    }

    // TODO: Mock Data returned. Will be changed
    public ApiResponse getDealerInventory(Integer dealerId){
        List<DealerInventoryDTO> list = new ArrayList<>();
        list.add(DealerInventoryDTO.builder()
                .orderId("Shipper1-01")
                .dateTime("25-03-2020")
                .codAmount(250.0)
                .prepaidAmount(0)
                .receiverName("Ali")
                .receiverContact("654784565")
                .trackingNumber("Shipper1-01")
                .status("Pending")
                .build());

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();

    }

    // TODO: Mock Data returned. Will be changed
    public ApiResponse getDealerServiceCharges(Integer dealerId){
        List<DealerServiceChargesDTO> list = new ArrayList<>();
        list.add(DealerServiceChargesDTO.builder()
                .dealerId(1)
                .currentBalance(33.56)
                .dueAmount(356)
                .ledgerId(2)
                .payable(45)
                .receivable(2)
                .build()

        );
        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();

    }

    // TODO: Mock Data returned. Will be changed
    public ApiResponse getDealerCODReturns(Integer dealerId){
        List<DealerCODReturnDTO> list = new ArrayList<>();
        list.add(DealerCODReturnDTO.builder()
                .dealerId(1)
                .currentBalance(44.56)
                .dueAmount(54)
                .ledgerId(6)
                .payable(63)
                .receivable(6)
                .build()
        );
        if (dealerId == 0) {
            list.add(DealerCODReturnDTO.builder()
                    .dealerId(2)
                    .currentBalance(90.0)
                    .dueAmount(30)
                    .ledgerId(8)
                    .payable(80)
                    .receivable(20)
                    .build()
            );
            list.add(DealerCODReturnDTO.builder()
                    .dealerId(3)
                    .currentBalance(300.0)
                    .dueAmount(250)
                    .ledgerId(10)
                    .payable(240)
                    .receivable(10)
                    .build()
            );
        }
        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();

    }

    private Dealer converFormDataToDealer(DealerFormDTO dealerForm){

        Dealer dealer = new Dealer();

        dealer.setOwnerName(dealerForm.getOwner());
        // First name is company name
        dealer.setCompanyName(dealerForm.getBusinessGroupName());
        //dealer.setCompanyNameAr();
        dealer.setContact(dealerForm.getContactNo());

        dealer.setAddress(dealerForm.getAddress());
        dealer.setCity(dealerForm.getCity());
        dealer.setZipCode(dealerForm.getPostCode());
        dealer.setCountry(dealerForm.getCountry());
        dealer.setNicNumber(dealerForm.getNicNumber());
        dealer.setNicFile(dealerForm.getIqamaFile());
        //dealer.setCrNumber(dealerForm.getCrNumber());
        //dealer.setCrFile(dealerForm.getCrFile());
        dealer.setLocationLatitude(dealerForm.getLocationLatitude());
        dealer.setLocationLongitude(dealerForm.getLocationLongitude());

        dealer.setCompensationClearanceDuration(dealerForm.getCompensationCycle());
        dealer.setPerShipmentsCompensation(dealerForm.getCompensationPerShipment());
        //dealer.setContractStartDate(DateUtil.toDateFromString(dealerForm.getContractStartDate()));
        //dealer.setContractEndDate(DateUtil.toDateFromString(dealerForm.getContractValidToDate()));
        dealer.setContractStartDate(dealerForm.getContractStartDate());
        dealer.setContractEndDate(dealerForm.getContractEndDate());
        dealer.setContractFile(dealerForm.getContractFile());
        dealer.setContractId(dealerForm.getContractId());

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBank(dealerForm.getBank());
        bankAccount.setAccountNumber(dealerForm.getIban());
        bankAccount.setIban(dealerForm.getIban());

        User user = new User();
        user.setFullName(dealerForm.getOwner());
        user.setUserName(dealerForm.getUserName());
        user.setUserPassword(dealerForm.getPassword());
        user.setEmail(dealerForm.getEmail());
        user.setContact(dealerForm.getContactNo());
        user.setDob(dealerForm.getDateOfBirth());
        user.setProfilePicture(dealerForm.getProfilePicture());
        user.setDesignation(UserRolesEnum.Dealer.toString());
        user.setUserType(UserRolesEnum.Dealer.toString());
        Optional<Role> dealerRole = roleService.getRoleByName(UserRolesEnum.Dealer.toString());

        if (dealerRole.isPresent()){
            user.setRole(dealerRole.get());
            user.setRoleId(dealerRole.get().getId());
        }

        if (dealerForm.getId() != null && dealerForm.getId() > 0){
            dealer.setId(dealerForm.getId());
            bankAccount.setId(dealerForm.getBankAccountId());
            user.setUserId(dealerForm.getUserId());
            bankAccountRepository.save(bankAccount);
            userRepository.save(user);
        } else {
            bankAccountRepository.save(bankAccount);
            userRepository.save(user);
        }

        dealer.setBankAccountId(bankAccount.getId());
        dealer.setBankAccount(bankAccount);

        dealer.setUserId(user.getUserId());

        return dealer;
    }
}
