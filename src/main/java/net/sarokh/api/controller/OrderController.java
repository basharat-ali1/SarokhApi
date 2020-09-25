package net.sarokh.api.controller;

import net.sarokh.api.model.*;
import net.sarokh.api.model.order.OrderNewFormDTO;
import net.sarokh.api.model.order.WebOrderDTO;
import net.sarokh.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
/*
    @RequestMapping(value = "/create-order", method = RequestMethod.POST)
    public ResponseEntity<?> createShipperOrder(@RequestBody List<OrderFormDTO> order){
        return ResponseEntity.ok(orderService.createShipperOrder(order));
    }

*/
    @RequestMapping(value = "/create-order", method = RequestMethod.POST)
    public ResponseEntity<?> createShipperOrder(@RequestBody List<OrderNewFormDTO> order){
        System.out.println("Create order controller called. " + order.size());
        return ResponseEntity.ok(orderService.createUpdateShipperOrder(order));
    }

    @RequestMapping(value = "/create-web-order", method = RequestMethod.POST)
    public ResponseEntity<?> createShipperWebOrder(@RequestBody List<WebOrderDTO> order){
        System.out.println("Create web order controller called. " + order.size());
        return ResponseEntity.ok(orderService.createShipperWebOrder(order));
    }

    @RequestMapping(value = "/create-bulk-order", method = RequestMethod.POST)
    public ResponseEntity<?> createShipperBulkOrder(@RequestBody OrderNewFormDTO order){
        return ResponseEntity.ok(orderService.createShipperBulkOrder(order));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrderDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @RequestMapping(value = "/edit-order/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> editOrderDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(orderService.editOrderDetail(id));
    }

    @RequestMapping(value = "/get-list/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersList(@PathVariable("shipperId") Integer shipperId){

        if (shipperId == -1){
            return ResponseEntity.ok(orderService.getOrdersList());
        } else {
            return ResponseEntity.ok(orderService.getAllShipmentsByShipperId(shipperId));
        }
    }

    @RequestMapping(value = "/get-order-shipments/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrderShipmentsList(@PathVariable("orderId") Integer orderId){
        return ResponseEntity.ok(orderService.getOrderShipmentList(orderId));
    }

    @RequestMapping(value = "/search-shipper-shipments/", method = RequestMethod.POST)
    public ResponseEntity<?> searchShipperLedgerShipments(@RequestBody SearchShipmentDTO shipmentDTO){
        return ResponseEntity.ok(orderService.searchShipperLedgerShipments(shipmentDTO));
    }

    @RequestMapping(value = "/print-bulk-shipments/", method = RequestMethod.POST)
    public ResponseEntity<?> printBulkShipments(@RequestBody SearchBulkShipmentDTO shipmentDTO){
        return ResponseEntity.ok(orderService.printBulkShipments(shipmentDTO));
    }

    @RequestMapping(value = "/search-shipment-by-trackingno/{trackingNumber}", method = RequestMethod.POST)
    public ResponseEntity<?> searchShipmentByTrackingNumber(@PathVariable String trackingNumber){
        return ResponseEntity.ok(orderService.searchShipmentByTrackingNumber(trackingNumber));
    }

    @RequestMapping(value = "/get-pickup-delivery-locations/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPickupDeliveryLocations(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.getPickupDeliveryLocations(shipperId));
    }

    // This is only for Print Label
    @RequestMapping(value = "/find-shipment-trackingno/{trackingNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> findShipmentByTrackingNumber(@PathVariable String trackingNumber){
        return ResponseEntity.ok(orderService.searchShipmentByTrackingNumber(trackingNumber));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrderDetails(@RequestBody List<WebOrderDTO> order){
        return ResponseEntity.ok(orderService.updateOrder(order));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Integer id){
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

    @RequestMapping(value = "/get-order-type/", method = RequestMethod.POST)
    public ResponseEntity<?> getOrdersListByType(@RequestBody OrderTypeInputDTO typeInputDTO){
        return ResponseEntity.ok(orderService.getOrdersByType(typeInputDTO));
    }

    @RequestMapping(value = "/create-order-id/{shipperId}", method = RequestMethod.POST)
    public ResponseEntity<?> createShipperNewOrderId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.createShipperNewOrderId(shipperId));
    }

    @RequestMapping(value = "/get-all-shipments/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllShipmentsByShipperId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.getAllShipmentsByShipperId(shipperId));
    }

    @RequestMapping(value = "/get-issue-shipments/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getIssueShipmentsByShipperId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.getIssueShipmentsByShipperId(shipperId));
    }

    @RequestMapping(value = "/get-COD-shipments/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCODShipmentsByShipperId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.getCODShipmentsByShipperId(shipperId));
    }

    @RequestMapping(value = "/get-returned-shipments/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getReturnedShipmentsByShipperId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.getReturnedShipmentsByShipperId(shipperId));
    }

    @RequestMapping(value = "/get-pending-orders/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPendingOrdersByShipperId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.getPendingOrderByShipperId(shipperId));
    }

    @RequestMapping(value = "/dashboard-orders-status/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDashboardOrdersStatus(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.findAllByDeliveryStatusAndPaymentType(shipperId));
    }

    @RequestMapping(value = "/get-all-shipments-trackingnumber/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllShipmentsTrackingNumberByShipperId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(orderService.getAllShipmentsTrackingNumberByShipperId(shipperId));
    }

    @RequestMapping(value = "/get-all-shipments-trackingnumber", method = RequestMethod.GET)
    public ResponseEntity<?> getAllShipmentsTrackingNumber(){
        return ResponseEntity.ok(orderService.getAllShipmentsTrackingNumber());
    }

}
