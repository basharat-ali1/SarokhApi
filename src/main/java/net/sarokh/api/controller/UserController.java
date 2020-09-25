package net.sarokh.api.controller;

import net.sarokh.api.ForgotPasswordDTO;
import net.sarokh.api.model.ChangePasswordDTO;
import net.sarokh.api.model.LoginUser;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.UserDTO;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.model.shipper.ShipperDashboard;
import net.sarokh.api.service.*;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShipperService shipperService;

    @Autowired
    private DealerService dealerService;

    @Autowired
    private DealerPointService dealerPointService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private WebDashboardsService webDashboardsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user){
        if (null == user || (null != user && null == user.getRoleId())){
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(ApplicationMessagesUtil.INVALID_USER_DETAILS)
                    .build());
        } else {
                userService.setUserRole(user);
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .data(userService.addUser(user))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build());
    }

    @RequestMapping(value = "/add-shipper-user/", method = RequestMethod.POST)
    public ResponseEntity<?> addShipperUser(@RequestBody User user){
        if (null != user){
            user.setUserType(UserRolesEnum.Shipper.toString());
            Optional<Role> shipperRole = roleService.getRoleByName(UserRolesEnum.Shipper.toString());

            if (shipperRole.isPresent()){
                user.setRole(shipperRole.get());
                user.setRoleId(shipperRole.get().getId());
            }
        }
        return ResponseEntity.ok(ApiResponse.builder()
                .data(userService.addUser(user))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build());
    }

    @RequestMapping(value = "/add-dealer-user/", method = RequestMethod.POST)
    public ResponseEntity<?> addDealerUser(@RequestBody User user){
        if (null != user){
            user.setUserType(UserRolesEnum.Dealer.toString());
            Optional<Role> dealerRole = roleService.getRoleByName(UserRolesEnum.DealerUser.toString());

            if (dealerRole.isPresent()){
                user.setRole(dealerRole.get());
                user.setRoleId(dealerRole.get().getId());
            }
        }
        return ResponseEntity.ok(ApiResponse.builder()
                .data(userService.addUser(user))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build());
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getUsersList(){
        return ResponseEntity.ok(userService.getAdminUsersList());
    }

    @RequestMapping(value = "/get-shipper-users-list/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperUsersList(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(userService.getShipperUsersList(shipperId));
    }

    @RequestMapping(value = "/get-dealer-users-list/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerUsersList(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(userService.getDealerUsersList(dealerId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateUserDetails(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.PATCH)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO passwordDTO){
        return ResponseEntity.ok(userService.changeUserPassword(passwordDTO));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody LoginUser loginUser){
        UserDTO user = userService.getUserByUsernameAndPassword(loginUser);

        if (null == user){
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(user)
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(ApplicationMessagesUtil.INVALID_USERNAME_PASSWORD)
                    .build());
        }

        if (null != user.getUserType() && user.getUserType().equals(UserRolesEnum.Shipper.toString())){
            Shipper shipper = shipperService.getShipperByUserId(user.getId());
            if (null != shipper){
                shipper.getUser().setUserPassword(null);
            }
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(shipper)
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build());
        }

        if (null != user.getUserType() && user.getUserType().equals(UserRolesEnum.DealerPoint.toString())){
            DealerPoint dealerPoint = dealerPointService.getDealerPointByUserId(user.getId());
            if (null != dealerPoint){
                dealerPoint.getUser().setUserPassword(null);

                return ResponseEntity.ok(ApiResponse.builder()
                    .data(dealerPoint)
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build());
            }
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .data(user)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build());
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public ResponseEntity<?> forgotUserPassword(@RequestBody ForgotPasswordDTO passwordDTO){
        if ( passwordDTO != null && passwordDTO.getContactNumber() != null ){
            if (userService.forgotPassword(passwordDTO.getContactNumber()) != null){
                return ResponseEntity.ok(ApiResponse.builder()
                        .data("Password is sent to provided contact number.")
                        .status(HttpStatus.OK.value())
                        .message(ApplicationMessagesUtil.SUCCESS)
                        .build());
            }
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .data(null)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ApplicationMessagesUtil.INVALID_INFO)
                .build());
    }

/*
    @RequestMapping(value = "/get-guest-user", method = RequestMethod.GET)
    public ResponseEntity<?> getUserGuest(){
        return ResponseEntity.ok(userService.getGuestUserForShipper());
    }
*/
}
