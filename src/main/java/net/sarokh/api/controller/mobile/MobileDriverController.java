package net.sarokh.api.controller.mobile;

import net.sarokh.api.model.LoginUser;
import net.sarokh.api.model.UserDTO;
import net.sarokh.api.model.entity.Driver;
import net.sarokh.api.model.entity.Vehicle;
import net.sarokh.api.model.mobile.*;
import net.sarokh.api.service.DriverService;
import net.sarokh.api.service.UserService;
import net.sarokh.api.service.VehicleService;
import net.sarokh.api.service.mobile.MobileDriverService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("mobile-driver")
public class MobileDriverController {

    @Autowired
    private UserService userService;

    @Autowired
    private MobileDriverService mobileDriverService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(value = "/login-driver", method = RequestMethod.POST)
    public ResponseEntity<?> loginDriver(@RequestBody LoginUser loginUser){
        UserDTO user = userService.getUserByUsernameAndPassword(loginUser);

        if (null == user){
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(user)
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(ApplicationMessagesUtil.INVALID_USERNAME_PASSWORD)
                    .build());
        }

        Optional<Driver> driver = driverService.getDriverByUserId(user.getId());

        return ResponseEntity.ok(ApiResponse.builder()
                .data(driver)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build());
    }

    @RequestMapping(value = "/get-driver-dashboard/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverDashboard(@PathVariable("driverId") Integer driverId){
        return ResponseEntity.ok(mobileDriverService.getMobileDriverDashboard(driverId));
    }

    @RequestMapping(value = "/get-sarokh-task/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverSarokhTask(@PathVariable("driverId") Integer driverId){
        return ResponseEntity.ok(mobileDriverService.getDriverSarokhTask(driverId));
    }

    @RequestMapping(value = "/get-driver-trips/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMobileDriverTrips(@PathVariable("driverId") Integer driverId){
        return ResponseEntity.ok(mobileDriverService.getMobileDriverTrips(driverId));
    }

    @RequestMapping(value = "/get-point-summary/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerPointSummaryForDriver(@PathVariable("taskId") Integer taskId){
        return ResponseEntity.ok(mobileDriverService.getDealerPointSummaryForDriver(taskId));
    }

    @RequestMapping(value = "/sarokh-task-confirm/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> confirmSarokhTask(@PathVariable("driverId") Integer driverId){
        return ResponseEntity.ok(mobileDriverService.confirmSarokhTask(driverId));
    }

    @RequestMapping(value = "/submit-driver-task-confirm", method = RequestMethod.POST)
    public ResponseEntity<?> submitDriverTaskConfirmation(@RequestBody SarokhTaskDriverConfirmDTO confirmDTO){
        return ResponseEntity.ok(mobileDriverService.submitDriverTaskConfirmation(confirmDTO));
    }

    @RequestMapping(value = "/get-receive-cod", method = RequestMethod.POST)
    public ResponseEntity<?> getDriverReceiveCOD(@RequestBody SearchDriverReceiveCodDTO codDTO){
        return ResponseEntity.ok(mobileDriverService.getDriverReceiveCOD(codDTO));
    }

    @RequestMapping(value = "/submit-receive-cod", method = RequestMethod.POST)
    public ResponseEntity<?> submitDriverReceiveCOD(@RequestBody DriverReceiveCodPaymentDTO codDTO){
        return ResponseEntity.ok(mobileDriverService.submitDriverReceiveCOD(codDTO));
    }

    @RequestMapping(value = "/get-give-shipments-detail/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getGiveShipmentsDetail(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(mobileDriverService.getGiveShipmentsDetail(dealerId));
    }

    @RequestMapping(value = "/get-driver-wallet/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverWallet(@PathVariable("driverId") Integer driverId){
        return ResponseEntity.ok(mobileDriverService.getDriverWalletBalance(driverId));
    }

    @RequestMapping(value = "/get-recieve-shipments-detail", method = RequestMethod.POST)
    public ResponseEntity<?> getReceiveShipmentsDetail(@RequestBody SearchDriverReceiveCodDTO requestDTO){
        return ResponseEntity.ok(mobileDriverService.getReceiveShipmentsDetail(requestDTO.getDriverId(), requestDTO.getDealerId()));
    }

    @RequestMapping(value = "/get-shipper-warehouse-shipments/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperWarehouseShipmentsDetail(@PathVariable("taskId") Integer taskId){
        return ResponseEntity.ok(mobileDriverService.getShipperWarehouseShipmentsDetail(taskId));
    }

    @RequestMapping(value = "/add-my-vehicle", method = RequestMethod.POST)
    public ResponseEntity<?> addVehicle(@RequestBody AddVehicleDTO vehicle){
        return ResponseEntity.ok(mobileDriverService.addDriverVehicle(vehicle));
    }

    @RequestMapping(value = "/receive-amount", method = RequestMethod.POST)
    public ResponseEntity<?> driverReceiveAmount(@RequestBody DriveReceiveAmountDTO receiveAmountDTO){
        return ResponseEntity.ok(mobileDriverService.driverReceiveAmount(receiveAmountDTO));
    }

}
