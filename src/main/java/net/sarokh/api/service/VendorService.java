package net.sarokh.api.service;

import net.sarokh.api.dao.BankAccountRepository;
import net.sarokh.api.dao.UserRepository;
import net.sarokh.api.dao.VendorRepository;
import net.sarokh.api.model.VendorDTO;
import net.sarokh.api.model.entity.BankAccount;
import net.sarokh.api.model.entity.Role;
import net.sarokh.api.model.entity.User;
import net.sarokh.api.model.entity.Vendor;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    public ApiResponse add(VendorDTO vendorDTO){

        Vendor vendor = createVendorObject(vendorDTO);

        return ApiResponse.builder()
                .data(repository.save(vendor))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.VENDOR_SUCCESSFULLY_CREATED)
                .build();
    }

    public ApiResponse getVendorById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse getVendorsList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse updateVendor(VendorDTO vendorDTO){

        Vendor vendor = createVendorObject(vendorDTO);

        return ApiResponse.builder()
                .data(repository.save(vendor))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.VENDOR_SUCCESSFULLY_UPDATED)
                .build();
    }

    public ApiResponse deleteVendor(Integer id) {
        repository.deleteById(id);
        return ApiResponse.builder()
                .data(id + "successfully deleted")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    private Vendor createVendorObject(VendorDTO vendorDTO){
        Vendor vendor = new Vendor();
        vendor.setCompanyName(vendorDTO.getCompanyName());
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setCity(vendorDTO.getCity());
        vendor.setContact(vendorDTO.getContact());
        vendor.setCountry(vendorDTO.getCountry());
        vendor.setEmail(vendorDTO.getEmail());
        vendor.setCommercialRegistrationNumber(vendorDTO.getCommercialRegistrationNumber());
        vendor.setCrFile(vendorDTO.getCrFile());
        vendor.setPaymentApiId(vendorDTO.getPaymentApiId());
        vendor.setPaymentApiKey(vendorDTO.getPaymentApiKey());
        vendor.setPaymentApiUrl(vendorDTO.getPaymentApiUrl());

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBank(vendorDTO.getBankName());
        bankAccount.setAccountNumber(vendorDTO.getBankAccountIban());
        bankAccount.setIban(vendorDTO.getBankAccountIban());

        User user = new User();
        user.setFullName(vendorDTO.getCompanyName());
        user.setUserName(vendorDTO.getUserName());
        user.setUserPassword(vendorDTO.getPassword());
        user.setEmail(vendorDTO.getEmail());
        user.setContact(vendorDTO.getContact());
        //user.setDob(vendorDTO.getDateOfBirth());
        //user.setProfilePicture(vendorDTO.getProfilePicture());
        user.setDesignation(UserRolesEnum.Vendor.toString());
        user.setUserType(UserRolesEnum.Vendor.toString());

        Optional<Role> vendorRole = roleService.getRoleByName(UserRolesEnum.Vendor.toString());

        if (vendorRole.isPresent()){
            user.setRole(vendorRole.get());
            user.setRoleId(vendorRole.get().getId());
        }

        if (vendorDTO.getId() != null && vendorDTO.getId() > 0){
            vendor.setId(vendorDTO.getId());
            bankAccount.setId(vendorDTO.getBankAccountId());
            user.setUserId(vendorDTO.getUserId());
            bankAccountRepository.save(bankAccount);
            userRepository.save(user);
        } else {
            bankAccountRepository.save(bankAccount);
            userRepository.save(user);
        }

        vendor.setBankAccountId(bankAccount.getId());
        vendor.setUserId(user.getUserId());

        return vendor;
    }
}
