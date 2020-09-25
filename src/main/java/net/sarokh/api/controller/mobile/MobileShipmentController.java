package net.sarokh.api.controller.mobile;

import net.sarokh.api.model.OrderStatusInputDTO;
import net.sarokh.api.model.mobile.CashHandoverDTO;
import net.sarokh.api.model.mobile.HandoverReceivedShipmentDTO;
import net.sarokh.api.model.mobile.HandoverShipmentSearchDTO;
import net.sarokh.api.model.mobile.ReceiveShipmentSearchDTO;
import net.sarokh.api.service.mobile.MobileShipmentService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipment-mobile")
public class MobileShipmentController {

    @Autowired
    private MobileShipmentService shipmentService;

    @RequestMapping(value = "/search-shipment-trackingNo/{trackingNo}", method = RequestMethod.GET)
    public ResponseEntity<?> searchShipmentByTrackingNumber(@PathVariable("trackingNo") String trackingNumber){
        return ResponseEntity.ok(shipmentService.getShipmentByTrackingNumber(trackingNumber));
    }

    @RequestMapping(value = "/search-receive-shipment", method = RequestMethod.POST)
    public ResponseEntity<?> searchReceiveShipment(@RequestBody ReceiveShipmentSearchDTO shipmentSearch){

        if (null != shipmentSearch && null != shipmentSearch.getReceivedFrom()){
            return ResponseEntity.ok(shipmentService.shipmentReceived(shipmentSearch));
        } else if (null != shipmentSearch && null != shipmentSearch.getReturnFrom()){
            return ResponseEntity.ok(shipmentService.returnShipmentFromDealer(shipmentSearch));
        }

        return ResponseEntity.ok( ApiResponse.builder()
                .message(ApplicationMessagesUtil.INVALID_TRACKING_NUMBER)
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @RequestMapping(value = "/handover-recieved-shipment/", method = RequestMethod.POST)
    public ResponseEntity<?> handoverReceivedShipment(@RequestBody HandoverReceivedShipmentDTO handoverReceivedShipment){
        return ResponseEntity.ok(shipmentService.handoverReceivedShipment(handoverReceivedShipment));
    }

    @RequestMapping(value = "/searchby-tracking-mobile/", method = RequestMethod.POST)
    public ResponseEntity<?> searchShipmentByTrackingAndMobile(@RequestBody HandoverShipmentSearchDTO shipmentSearchDTO){
        return ResponseEntity.ok(shipmentService.getShipmentByTrackingAndMobile(shipmentSearchDTO));
    }


    @RequestMapping(value = "/get-dealer-shipments/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerShipmentsList(@PathVariable("dealerId") String dealerId){
        return ResponseEntity.ok(shipmentService.getDealerShipments(dealerId));
    }

    @RequestMapping(value = "/get-order-status/", method = RequestMethod.POST)
    public ResponseEntity<?> getOrdersListByStatus(@RequestBody OrderStatusInputDTO orderStatusInputDTO){
        return ResponseEntity.ok(shipmentService.getOrdersByStatus(orderStatusInputDTO));
    }

    @RequestMapping(value = "/get-agents-drivers/{dealerId}", method = RequestMethod.POST)
    public ResponseEntity<?> getAgentsDriversList(@PathVariable("dealerId") String dealerId){
        return ResponseEntity.ok(shipmentService.getAgentsDriversList(dealerId));
    }

    @RequestMapping(value = "/cash-handover-dealer/", method = RequestMethod.POST)
    public ResponseEntity<?> handoverCashToAgentOrDriver(@RequestBody CashHandoverDTO cashHandover){
        return ResponseEntity.ok(shipmentService.handoverCash(cashHandover));
    }

}
