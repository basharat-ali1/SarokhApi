package net.sarokh.api.controller;

import net.sarokh.api.model.entity.ShipperWarehouse;
import net.sarokh.api.service.ShipperWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipper-warehouse")
public class ShipperWarehouseController {

    @Autowired
    private ShipperWarehouseService shipperWarehouseService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addShipperWarehouse(@RequestBody ShipperWarehouse shipperWarehouse){
        return ResponseEntity.ok(shipperWarehouseService.addShipperWarehouse(shipperWarehouse));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperWarehouseDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(shipperWarehouseService.getShipperWarehouseById(id));
    }

    @RequestMapping(value = "/get-list/", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperWarehousesList(){
        return ResponseEntity.ok(shipperWarehouseService.getShipperWarehousesList());
    }

    @RequestMapping(value = "/get-list-by-shipperId/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperWarehousesListByShipperId(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(shipperWarehouseService.getShipperWarehousesListByShipperId(shipperId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateShipperWarehouseDetails(@RequestBody ShipperWarehouse shipperWarehouse){
        return ResponseEntity.ok(shipperWarehouseService.updateShipperWarehouse(shipperWarehouse));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShipperWarehouse(@PathVariable("id") int id){
        return ResponseEntity.ok(shipperWarehouseService.deleteShipperWarehouse(id));
    }

}
