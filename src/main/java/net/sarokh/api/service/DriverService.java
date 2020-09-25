package net.sarokh.api.service;

import net.sarokh.api.dao.*;
import net.sarokh.api.model.DateTimeDTO;
import net.sarokh.api.model.enums.*;
import net.sarokh.api.model.driver.DriverFormDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.mobile.*;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DriverService {

    @Autowired
    private DriverRepository repository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private DispatchTripRepository dispatchTripRepository;

    public ApiResponse addDriver (DriverFormDTO driverForm){

        Driver driver = convertToDriver(driverForm);

        repository.save(driver);

        createDriverWallets(driver);

        return ApiResponse.builder()
                .data(driver)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DRIVER_SUCCESSFULLY_CREATED)
                .build();
    }

    private void createDriverWallets(Driver driver){
        List<Wallet> wallets = new ArrayList<>();
        String fullName = driver.getFirstName() + " " + driver.getLastName();
        wallets.add(walletService.createWalletObject("Driver Collection Wallet", WalletTypeEnum.DriverCollection.getWalletType(),
                driver.getId(), UserRolesEnum.Driver.name(), driver.getUserId(), fullName));
        wallets.add(walletService.createWalletObject("Driver Compensation Wallet", WalletTypeEnum.DriverCompensation.getWalletType(),
                driver.getId(), UserRolesEnum.Driver.name(), driver.getUserId(), fullName));
        walletService.addWallets(wallets);
    }

    public Optional<Driver> getDriverById(Integer id){
        Optional<Driver> driver = repository.findById(id);

       /* if (driver.isPresent()){
            if (null != driver.get().getContractStartDate()){
                driver.get().setContractStartDate(DateUtil.toDateFromString(driver.get().getContractStartDate().toString()));
            }
            if (null != driver.get().getContractValidTill()) {
                driver.get().setContractValidTill(DateUtil.toDateFromString(driver.get().getContractValidTill().toString()));
            }
        }*/

        return driver;
    }

    public Optional<Driver> getDriverByUserId(Integer userId){
        return repository.findByUserId(userId);
    }

    public Iterable<Driver> getDriversList() {
        return repository.findAll();
    }

    public ApiResponse updateDriver (DriverFormDTO driverForm){
        Driver driver = convertToDriver(driverForm);

        repository.save(driver);

        return ApiResponse.builder()
                .data(driver)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DRIVER_SUCCESSFULLY_UPDATED)
                .build();
    }

    public ApiResponse deleteDriver (Integer id){

        if (null != id) {

            Optional<Driver> driver = repository.findById(id);

            if (driver.isPresent()){
                repository.delete(driver.get());

                return ApiResponse.builder()
                        .data("Driver Id=" + id + " Successfully deleted.")
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


    private Driver convertToDriver(DriverFormDTO driverForm){
        Driver driver = new Driver();
        driver.setFirstName(driverForm.getFirstName());
        driver.setLastName(driverForm.getLastName());
        driver.setLicenseNumber(driverForm.getLicenseNumber());
        driver.setLicenseFile(driverForm.getLicenseFile());
        driver.setNicNumber(driverForm.getNicNumber());
        driver.setNicFile(driverForm.getNicFile());
        driver.setAddress(driverForm.getAddress());
        driver.setCity(driverForm.getCity());
        driver.setPostalCode(driverForm.getPostCode());
        driver.setCountry(driverForm.getCountry());
        driver.setWarehouseId(driverForm.getWarehouseId());

        driver.setDriverType(driverForm.getDriverType());

        driver.setContractFile(driverForm.getContractFile());
        driver.setCompensation(driverForm.getCompensation());
        driver.setCompensationCycle(driverForm.getCompensationCycle());
        //driver.setContractStartDate(DateUtil.toDateFromString(driverForm.getContractStartDate()));
        //driver.setContractValidTill(DateUtil.toDateFromString(driverForm.getContactValidTill()));
        driver.setContractStartDate(driverForm.getContractStartDate());
        driver.setContractValidTill(driverForm.getContactValidTill());

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBank(driverForm.getBank());
        bankAccount.setAccountNumber(driverForm.getIban());
        bankAccount.setIban(driverForm.getIban());

        User user = new User();
        user.setFullName(driverForm.getFirstName() + " " +
                driverForm.getLastName());
        user.setUserName(driverForm.getUserName());
        user.setUserPassword(driverForm.getPassword());
        user.setEmail(driverForm.getEmail());
        user.setContact(driverForm.getContact());
        user.setDob(driverForm.getDateOfBirth());
        user.setProfilePicture(driverForm.getProfilePicture());
        user.setDesignation(UserRolesEnum.Driver.toString());
        user.setUserType(UserRolesEnum.Driver.toString());

        Optional<Role> driverRole = roleService.getRoleByName(UserRolesEnum.Driver.toString());

        if (driverRole.isPresent()){
            user.setRole(driverRole.get());
            user.setRoleId(driverRole.get().getId());
        }

        if (driverForm.getId() != null && driverForm.getId() > 0){
            driver.setId(driverForm.getId());
            bankAccount.setId(driverForm.getBankAccountId());
            user.setUserId(driverForm.getUserId());
            bankAccountRepository.save(bankAccount);
            userRepository.save(user);
        } else {
            bankAccountRepository.save(bankAccount);
            userRepository.save(user);
        }

        driver.setBankAccountId(bankAccount.getId());
        driver.setBankAccount(bankAccount);
        driver.setUserId(user.getUserId());

        if (null != driverForm.getDriverType() && driverForm.getDriverType().equals(DriverTypeEnum.FreeLancer.name())){
            Vehicle vehicle = new Vehicle();
            vehicle.setName(driverForm.getVehicleName());
            vehicle.setRegistrationNumber(driverForm.getRegistrationNumber());
            vehicle.setRegistrationFile(driverForm.getRegistrationFile());
            vehicle.setModel(driverForm.getVehicleModel());
            vehicle.setMake(driverForm.getMake());
            vehicle.setRegistrationYear(driverForm.getRegistrationYear());
            vehicle.setType(driverForm.getType());
            vehicle.setCargoCapacity(driverForm.getCargoCapacity());
            vehicle.setOwner(VehicleOwnerEnum.OperatorOwned.name());
            vehicle.setProductionYear(driverForm.getProductionYear());
            vehicle.setStatus("Active");

            if (driverForm.getId() != null && driverForm.getId() > 0){
                vehicle.setId(driverForm.getVehicleId());
            }

            vehicleRepository.save(vehicle);
            driver.setVehicleId(vehicle.getId());
            driver.setDriverType(DriverTypeEnum.FreeLancer.name());
        } else {
            driver.setDriverType(DriverTypeEnum.Employee.name());
        }

        return driver;
    }

    private class StopsMapKey{
        Integer id;
        String location;
        public StopsMapKey(Integer id,String location){
            this.id = id;
            this.location = location;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
