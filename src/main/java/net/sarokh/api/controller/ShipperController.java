package net.sarokh.api.controller;

import net.sarokh.api.model.entity.DeliveryCharges;
import net.sarokh.api.model.entity.Shipper;
import net.sarokh.api.model.shipper.ShipperFormDTO;
import net.sarokh.api.service.DeliveryChargesService;
import net.sarokh.api.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipper")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @Autowired
    private DeliveryChargesService deliveryChargesService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addShipper(@RequestBody ShipperFormDTO shipperForm){
        return ResponseEntity.ok(shipperService.addShipper(shipperForm));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(shipperService.getShipperById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShippersList(){
        return ResponseEntity.ok(shipperService.getShippersList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateShipperDetails(@RequestBody Shipper shipper){
        return ResponseEntity.ok(shipperService.updateShipper(shipper));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShipper(@PathVariable("id") Integer id){
        shipperService.deleteShipper(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

    @RequestMapping(value = "/get-list-admin", method = RequestMethod.GET)
    public ResponseEntity<?> getShippersListAdmin(){
        return ResponseEntity.ok(shipperService.getShippersList());
    }

    @RequestMapping(value = "/add-delivery-charges", method = RequestMethod.POST)
    public ResponseEntity<?> addShipperDeliveryCharges(@RequestBody DeliveryCharges deliveryCharges){
        return ResponseEntity.ok(deliveryChargesService.add(deliveryCharges));
    }

    @RequestMapping(value = "/get-delivery-charges-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShippersDeliveryChargesList(){
        return ResponseEntity.ok(deliveryChargesService.getDeliveryChargesList());
    }

    @RequestMapping(value = "/get-shipper-delivery-charges/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getShippersDeliveryCharges(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(deliveryChargesService.getDeliveryByShipperId(shipperId));
    }

}
