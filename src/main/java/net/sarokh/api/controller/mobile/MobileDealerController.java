package net.sarokh.api.controller.mobile;


import net.sarokh.api.model.mobile.*;
import net.sarokh.api.service.mobile.MobileDealerService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dealer-mobile")
public class MobileDealerController {

    @Autowired
    private MobileDealerService mobileDealerService;

    @RequestMapping(value = "/dashboard/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> getDealerDashboard(@PathVariable("id") Integer id){

        if (null == id || 0 == id){
            return ResponseEntity.ok(ApiResponse.builder()
                    .message(ApplicationMessagesUtil.INVALID_USER_DETAILS)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build());
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .data(mobileDealerService.getDealerDashboard(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build());

    }

    @RequestMapping(value = "/recieve-shipment/", method = RequestMethod.POST)
    public ResponseEntity<?> receiveShipment(@RequestBody DealerReceiveShipmentDTO receiveShipmentDTO){
        return ResponseEntity.ok(mobileDealerService.receiveShipment(receiveShipmentDTO));
    }

    @RequestMapping(value = "/get-recieve-shipments-detail/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getReceiveShipmentsDetail(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(mobileDealerService.getReceiveShipmentsDetail(dealerId));
    }

    @RequestMapping(value = "/get-give-shipments-detail/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getGiveShipmentsDetail(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(mobileDealerService.getGiveShipmentsDetail(dealerId));
    }

    @RequestMapping(value = "/get-pay-cod-detail/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPayCODDetail(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(mobileDealerService.getPayCODDetail(dealerId));
    }

    @RequestMapping(value = "/get-cod-ledger/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCODLedger(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(mobileDealerService.getCODLedger(userId));
    }

    @RequestMapping(value = "/get-shipper-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperListWithPendingOrders(){
        return ResponseEntity.ok(mobileDealerService.getShippersListWithPendingOrders());
    }

    @RequestMapping(value = "/get-shipper-receive/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperReceiveList(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(mobileDealerService.getShipperReceiveList(shipperId));
    }

    @RequestMapping(value = "/get-sarokh-task/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getSarokhTask(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(mobileDealerService.getSarokhTask(dealerId));
    }

    @RequestMapping(value = "/submit-sarokh-task/", method = RequestMethod.POST)
    public ResponseEntity<?> submitSarokhTask(@RequestBody SarokhTaskSubmitDTO sarokhTaskDTO){
        return ResponseEntity.ok(mobileDealerService.submitSarokhTask(sarokhTaskDTO));
    }

    @RequestMapping(value = "/request-sarokh-task-confirmation/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> requestSarokhTaskConfirmation(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(mobileDealerService.requestSarokhTaskConfirmation(dealerId));
    }

    @RequestMapping(value = "/search-shipment-trackingNo/{trackingNo}", method = RequestMethod.GET)
    public ResponseEntity<?> searchShipmentByTrackingNumber(@PathVariable("trackingNo") String trackingNumber){
        return ResponseEntity.ok(mobileDealerService.searchShipmentByTrackingNumber(trackingNumber));
    }

    @RequestMapping(value = "/handover-recieved-shipment/", method = RequestMethod.POST)
    public ResponseEntity<?> handoverReceivedShipment(@RequestBody HandoverReceivedShipmentDTO handoverReceivedShipment){
        return ResponseEntity.ok(mobileDealerService.handoverReceivedShipment(handoverReceivedShipment));
    }

    @RequestMapping(value = "/report-issue-complaint/", method = RequestMethod.POST)
    public ResponseEntity<?> reportIssueComplaint(@RequestBody ReportIssueDTO reportIssueDTO){
        return ResponseEntity.ok(mobileDealerService.reportIssueComplaint(reportIssueDTO));
    }

    @RequestMapping(value = "/confirm-shipper-receive-shipments/", method = RequestMethod.POST)
    public ResponseEntity<?> confirmShipperReceiveShipments(@RequestBody MobileShippersListDTO shippersListDTO){
        return ResponseEntity.ok(mobileDealerService.confirmShipperReceiveShipments(shippersListDTO));
    }

    @RequestMapping(value = "/create-mobile-shipment/", method = RequestMethod.POST)
    public ResponseEntity<?> createMobileShipment(@RequestBody MobileCreateShipmentDTO mobileShipmentDTO){
        return ResponseEntity.ok(mobileDealerService.createMobileShipment(mobileShipmentDTO));
    }

    @RequestMapping(value = "/get-bill/", method = RequestMethod.POST)
    public ResponseEntity<?> getBill(@RequestBody SarokhPayDTO billDTO){
        return ResponseEntity.ok(mobileDealerService.getBill(billDTO));
    }

    @RequestMapping(value = "/pay-sarokh-paymnet/", method = RequestMethod.POST)
    public ResponseEntity<?> paySarokhPayment(@RequestBody SarokhPayDTO billDTO){
        return ResponseEntity.ok(mobileDealerService.paySarokhPayment(billDTO));
    }

    @RequestMapping(value = "/verify-shipment-trackingNo/{trackingNo}", method = RequestMethod.GET)
    public ResponseEntity<?> verifyTrackingNumberAndReturnShipper(@PathVariable("trackingNo") String trackingNumber){
        return ResponseEntity.ok(mobileDealerService.verifyTrackingNumberAndReturnShipper(trackingNumber));
    }

    @RequestMapping(value = "/get-my-wallet/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMyWallet(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(mobileDealerService.getDealerWalletDetail(dealerId));
    }

/*
    @RequestMapping(value = "/confirm-sarokh-task/", method = RequestMethod.POST)
    public ResponseEntity<?> confirmSarokhTask(@RequestBody SarokhTaskConfirmDTO sarokhTaskConfirmDTO){
        return ResponseEntity.ok(mobileDealerService.confirmSarokhTask(shipmentIdList));
    }
*/
}
