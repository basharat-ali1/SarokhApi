package net.sarokh.api.controller;

import net.sarokh.api.model.shipper.ShipperAddressDTO;
import net.sarokh.api.model.entity.ShipperAddress;
import net.sarokh.api.service.ShipperAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipper-address")
public class ShipperAddressController {

    @Autowired
    private ShipperAddressService shipperAddressService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addShipperAddress(@RequestBody ShipperAddress shipperAddress){
        return ResponseEntity.ok(shipperAddressService.addShipperAddress(shipperAddress));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperAddressDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(shipperAddressService.getShipperAddressById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperAddresssList(){
        return ResponseEntity.ok(shipperAddressService.getShipperAddresssList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<?> updateShipperAddressDetails(@RequestBody ShipperAddressDTO shipperAddress){
        ResponseEntity<?> response = null;
        return response;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteShipperAddress(@PathVariable("id") int id){
        ResponseEntity<?> response = null;
        return response;
    }

}
