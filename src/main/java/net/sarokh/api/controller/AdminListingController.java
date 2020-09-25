package net.sarokh.api.controller;

import net.sarokh.api.model.enums.OrderDeliveryStatusEnum;
import net.sarokh.api.model.enums.PaymentTypeEnum;
import net.sarokh.api.model.enums.ShipmentStatusEnum;
import net.sarokh.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminListingController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SarokhWarehouseService sarokhWarehouseService;

    @Autowired
    private DispatchTripService dispatchTripService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private DealerService dealerService;


    @RequestMapping(value = "/get-all-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllShipments(){
        return ResponseEntity.ok(orderService.findAllShipments());
    }

    @RequestMapping(value = "/get-delivered-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getDeliveredShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByDeliveryStatus(OrderDeliveryStatusEnum.Delivered.name()));
    }

    @RequestMapping(value = "/get-pending-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getPendingShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByDeliveryStatus(OrderDeliveryStatusEnum.Pending.name()));
    }

    @RequestMapping(value = "/get-noresponse-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getNoResponseShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByDeliveryStatus(OrderDeliveryStatusEnum.NoResponse.name()));
    }

    @RequestMapping(value = "/get-returned-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getReturnedShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByDeliveryStatus(OrderDeliveryStatusEnum.Returned.name()));
    }

    @RequestMapping(value = "/get-shipment-issues/", method = RequestMethod.GET)
    public ResponseEntity<?> getShipmentIssues(){
        return ResponseEntity.ok(orderService.findShipmentsWithIssues());
    }

    @RequestMapping(value = "/get-cod-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getCODShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByPaymentType(PaymentTypeEnum.COD.name()));
    }

    @RequestMapping(value = "/get-prepaid-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getPrepaidShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByPaymentType(PaymentTypeEnum.Prepaid.name()));
    }

    @RequestMapping(value = "/get-pickup-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getPickupShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByDeliveryOrPickup(ShipmentStatusEnum.Pickup.name()));
    }

    @RequestMapping(value = "/get-delivery-shipments/", method = RequestMethod.GET)
    public ResponseEntity<?> getDeliveryShipments(){
        return ResponseEntity.ok(orderService.findShipmentsByDeliveryOrPickup(ShipmentStatusEnum.Delivery.name()));
    }

    @RequestMapping(value = "/get-orders-location/", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersLocations(){
        return ResponseEntity.ok(orderService.getOrdersLocation());
    }

    //      -----------------    Warehouse --------------------

    @RequestMapping(value = "/get-warehouse-inventory", method = RequestMethod.GET)
    public ResponseEntity<?> getWarehouseInventoryManagement(){
        return ResponseEntity.ok(sarokhWarehouseService.getWarehouseInventoryManagement());
    }

    //      -----------------    Trips --------------------

    @RequestMapping(value = "/get-all-trips", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTrips(){
        return ResponseEntity.ok(dispatchTripService.getAllTripsForAdmin());
    }

    @RequestMapping(value = "/get-active-trips", method = RequestMethod.GET)
    public ResponseEntity<?> getActiveTrips(){
        return ResponseEntity.ok(dispatchTripService.getActiveTripsForAdmin());
    }

    //      -----------------    Dealer --------------------

    @RequestMapping(value = "/get-dealer-inventory", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerInventory(){
        return ResponseEntity.ok(orderService.findDealerInventory());
    }

    @RequestMapping(value = "/get-dealer-received-ledgers", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerReceivedLedgers(){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/get-dealer-cod-ledgers", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerCODLedgers(){
        return ResponseEntity.ok(dealerService.getDealerCODReturns(0));
    }

    //      -----------------    Vehicle --------------------

    @RequestMapping(value = "/get-shipper-billing", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperBilling(){
        return ResponseEntity.ok(ledgerService.getShipperBilling());
    }

}
