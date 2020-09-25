package net.sarokh.api.service;

import net.sarokh.api.dao.ShipperRepository;
import net.sarokh.api.dao.UserRepository;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.model.enums.WalletTypeEnum;
import net.sarokh.api.model.shipper.ShipperFormDTO;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShipperService {

    @Autowired
    private ShipperRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletService walletService;

    public ApiResponse addShipper (ShipperFormDTO shipperForm){

        Shipper newShipper = createShipperObject(shipperForm);

        User user = new User();
        user.setFullName(shipperForm.getShipperBasicInfo().getFirstName() + " " +
                shipperForm.getShipperBasicInfo().getLastName());
        user.setUserName(shipperForm.getSecurity().getUsername());
        user.setUserPassword(shipperForm.getSecurity().getPassword());
        user.setEmail(shipperForm.getShipperBasicInfo().getEmail());
        user.setContact(shipperForm.getShipperBasicInfo().getContact());
        user.setDob(shipperForm.getShipperBasicInfo().getDateOfBirth());
        user.setProfilePicture(shipperForm.getShipperBasicInfo().getProfilePicture());
        user.setDesignation(UserRolesEnum.Shipper.toString());
        user.setUserType(UserRolesEnum.Shipper.toString());

        Optional<Role> shipperRole = roleService.getRoleByName(UserRolesEnum.Shipper.toString());

        if (shipperRole.isPresent()){
            user.setRole(shipperRole.get());
            user.setRoleId(shipperRole.get().getId());
        }

        User newUser = userRepository.save(user);
        newShipper.setUserId(user.getUserId());

        repository.save(newShipper);

        createShipperWallets(newShipper);

        return ApiResponse.builder()
                .data(newShipper)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SHIPPER_SUCCESSFULLY_CREATED)
                .build();
    }

    private void createShipperWallets(Shipper shipper){
        List<Wallet> wallets = new ArrayList<>();
        String fullName = shipper.getFirstName() + " " + shipper.getLastName();
        wallets.add(walletService.createWalletObject("Shipper COD Wallet", WalletTypeEnum.ShipperCod.getWalletType(),
                shipper.getId(), UserRolesEnum.Shipper.name(), shipper.getUserId(), fullName));
        wallets.add(walletService.createWalletObject("Shipper Delivery Charges Wallet", WalletTypeEnum.ShipperDeliveryCharges.getWalletType(),
                shipper.getId(), UserRolesEnum.Shipper.name(), shipper.getUserId(), fullName));
        walletService.addWallets(wallets);
    }

    public Optional<Shipper> getShipperById(Integer id){
        return repository.findById(id);
    }

    public Shipper getShipperByUserId(Integer userId){
        return repository.findByUserId(userId);
    }

    public Iterable<Shipper> getShippersList() {
        Iterable<Shipper> shippers = repository.findAll();
        shippers.forEach(shipper -> {

        });
        return shippers;
    }

    public Shipper updateShipper (Shipper shipper){
        return repository.save(shipper);
    }

    public void deleteShipper (Integer id){
        repository.deleteById(id);
    }

    private Shipper createShipperObject(ShipperFormDTO shipperForm){
        Shipper newShipper = new Shipper();
        newShipper.setFirstName(shipperForm.getShipperBasicInfo().getFirstName());
        newShipper.setLastName(shipperForm.getShipperBasicInfo().getLastName());
        newShipper.setNicNumber(shipperForm.getShipperBusinessDetail().getIqamaNumber());
        newShipper.setNicFile(shipperForm.getShipperBusinessDetail().getIqamaFile());
        newShipper.setVatNumber(shipperForm.getShipperBusinessDetail().getVatNumber());
        newShipper.setCrNumber(shipperForm.getShipperBusinessDetail().getCrNumber());
        newShipper.setCrFile(shipperForm.getShipperBusinessDetail().getCrFile());
        newShipper.setShipperType(shipperForm.getShipperType());
        newShipper.setStatus("Active");


        return newShipper;
    }
}
