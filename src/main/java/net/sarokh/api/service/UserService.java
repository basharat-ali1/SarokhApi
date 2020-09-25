package net.sarokh.api.service;

import net.sarokh.api.dao.RoleRepository;
import net.sarokh.api.dao.UserRepository;
import net.sarokh.api.model.ChangePasswordDTO;
import net.sarokh.api.model.LoginUser;
import net.sarokh.api.model.UserDTO;
import net.sarokh.api.model.entity.Role;
import net.sarokh.api.model.entity.User;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RestTemplate restTemplate;

    public UserDTO addUser (User newUser){

        User user = repository.save(newUser);
        UserDTO userDTO = convertUserToUserDTO(user);
        //walletService.addWallet();
        //setUserRole (user, userDTO);
        setPasswordNull(userDTO);

        return userDTO;
    }

    public ApiResponse getUserById(Integer id){
        Optional<User> user = repository.findById(id);

        if (user.isPresent()){
            UserDTO userDTO = convertUserToUserDTO(user.get());
           // setUserRole (user.get(), userDTO);
            setPasswordNull(userDTO);
            Optional<Role> role = roleRepository.findById(user.get().getRoleId());
            if (role.isPresent()){
                userDTO.setRole(role.get());
            }
            return ApiResponse.builder()
                    .data(userDTO)
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build();
        }

        return ApiResponse.builder()
                .message(ApplicationMessagesUtil.INVALID_USER_DETAILS)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public Iterable<User> getAdminUsersList() {
        List<String> rolesList = new ArrayList<>();
        rolesList.add(UserRolesEnum.Admin.toString());
        rolesList.add(UserRolesEnum.WarehouseUser.toString());
        rolesList.add(UserRolesEnum.WarehouseSupervisor.toString());
        rolesList.add(UserRolesEnum.Driver.toString());

        Iterable<User> userList = repository.findUserByUserTypeIn(rolesList);
        userList.forEach(user -> {
            user.setUserPassword(null);
           // user.setDob(ApplicationUtil.isNotNullAndEmpty(user.getDob()) ? DateUtil.convertToDateAndTime(DateUtil.toDateFromString(user.getDob()), "yyyy-MM-dd").getDate() : null);
        });
        return userList;
    }

    public Iterable<User> getDealerUsersList(Integer dealerId) {
        Iterable<User> userList = repository.findUserByUserTypeAndParentTypeId(UserRolesEnum.Dealer.toString(), dealerId);
        userList.forEach(user -> {
            user.setUserPassword(null);
            //user.setDob(ApplicationUtil.isNotNullAndEmpty(user.getDob()) ? DateUtil.convertToDateAndTime(DateUtil.toDateFromString(user.getDob()), "yyyy-MM-dd").getDate() : null);
        });
        return userList;
    }

    public Iterable<User> getShipperUsersList(Integer shipperId) {
        Iterable<User> userList = repository.findUserByUserTypeAndParentTypeId(UserRolesEnum.Shipper.toString(), shipperId);
        userList.forEach(user -> {
            user.setUserPassword(null);
           // user.setDob(ApplicationUtil.isNotNullAndEmpty(user.getDob()) ? DateUtil.convertToDateAndTime(DateUtil.toDateFromString(user.getDob()), "yyyy-MM-dd").getDate():null);
        });
        return userList;
    }

    public UserDTO getUserByUsernameAndPassword(LoginUser loginUser){
        User user = repository.findUserByUserNameAndUserPassword(loginUser.getUsername(), loginUser.getPassword());

        if ( null != user){
            //UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getUserId())
                    .fullName(user.getFullName())
                    .userName(user.getUserName())
                    .designation(user.getDesignation())
                    .email(user.getEmail())
                    .gender(user.getGender())
                    .role(user.getRole())
                    .userType(user.getUserType())
                    .profilePicture(user.getProfilePicture())
                    .parentTypeId(user.getParentTypeId())
                    .build();

                  //  setUserRole (user, userDTO);
            //setPasswordNull(userDTO);

            return userDTO;
        }

        return null;
    }

    public User updateUser(User user){
        Optional<User> dbUser = repository.findById(user.getUserId());

        if (dbUser.isPresent()){
            //user.setUserPassword(dbUser.get().getUserPassword());

            if (dbUser.get().getUserType().equals(UserRolesEnum.Dealer.name()) || dbUser.get().getUserType().equals(UserRolesEnum.Shipper.name())) {
                user.setRoleId(dbUser.get().getRoleId());
                user.setUserType(dbUser.get().getUserType());
                user.setParentTypeId(dbUser.get().getParentTypeId());
            } else {
                setUserRole(user);
            }

            user.setProfilePicture(dbUser.get().getProfilePicture());
            user.setCreatedDatetime(dbUser.get().getCreatedDatetime());

            return repository.save(user);
        }

        user.setUserPassword(null);
        return user;

    }

    public ApiResponse changeUserPassword(ChangePasswordDTO passwordDTO){
        Optional<User> user = repository.findById(passwordDTO.getUserId());

        if (user.isPresent() && user.get().getUserPassword().equals(passwordDTO.getCurrentPassword())){
            user.get().setUserPassword(passwordDTO.getNewPassword());
            repository.save(user.get());

            return ApiResponse.builder()
                    .message(ApplicationMessagesUtil.PASSWORD_SUCCESSFULLY_CHANGED)
                    .status(HttpStatus.OK.value())
                    .build();

        } else if (user.isPresent() && !user.get().getUserPassword().equals(passwordDTO.getCurrentPassword())){
            return ApiResponse.builder()
                    .message(ApplicationMessagesUtil.INVALID_CURRENT_PASSWORD)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        } else {
            return ApiResponse.builder()
                    .message(ApplicationMessagesUtil.INVALID_USER_DETAILS)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

    }

    public ApiResponse deleteUser(Integer id){
        repository.deleteByUserId(id);
        return ApiResponse.builder()
                .data("User Successfully deleted.")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }
 /*
    private void setUserRole(User user, UserDTO userDTO){

       if (user != null && user.getRoleId() != null && userDTO != null){
            Optional<Role> role = roleRepository.findById(user.getRoleId());
            Role userRole = role.get();
            userDTO.setRole(userRole);

    }
}*/
    private void setPasswordNull(UserDTO userDTO){

        if (userDTO != null){
            userDTO.setUserPassword(null);
        }
    }

    public UserDTO convertUserToUserDTO(User user){
        Date date = DateUtil.toDateFromString(user.getDob());
        return UserDTO.builder()
                .id(user.getUserId())
                .fullName(user.getFullName())
                .userName(user.getUserName())
                .userPassword(user.getUserPassword())
                .designation(user.getDesignation())
                .userType(user.getUserType())
                .profilePicture(user.getProfilePicture())
                .contact(user.getContact())
                .email(user.getEmail())
                .gender(user.getGender())
                .dob(user.getDob())
                .parentTypeId(user.getParentTypeId())
                .createdDatetime(user.getCreatedDatetime())
                .updatedDatetime(user.getUpdatedDatetime())
                .build();
    }

    public void setUserRole(User user){
        Optional<Role> adminRole = roleRepository.findById(user.getRoleId());
        if (adminRole.isPresent()){
            if (adminRole.get().getName().equals(UserRolesEnum.WarehouseSupervisor.name())){
                Optional<Role> warehouseRole = roleRepository.getRoleByName(UserRolesEnum.WarehouseUser.name());
                user.setUserType(warehouseRole.get().getName());
                user.setRoleId(warehouseRole.get().getId());
            } else {
                user.setUserType(adminRole.get().getName());
                user.setRoleId(adminRole.get().getId());
            }
        }
    }

    public String forgotPassword(String contactNumber){

        Optional<User> user = repository.findByContact(contactNumber);

        if (user.isPresent()){
            String message = "Your account password: " + user.get().getUserPassword();
            return SmsUtil.sendSMS(restTemplate, contactNumber, message);
        }

        return null;
    }

    public User getGuestUserForShipper(){

        Optional<User> user = repository.findByUserType(UserRolesEnum.Guest.name());

        if (user.isPresent()){
            return user.get();
        }

        return null;
    }
}