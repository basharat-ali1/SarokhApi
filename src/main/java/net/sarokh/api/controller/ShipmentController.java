package net.sarokh.api.controller;

import net.sarokh.api.model.ShipmentDTO;
import net.sarokh.api.model.entity.Shipment;
import net.sarokh.api.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addShipment(@RequestBody Shipment shipment){
        return ResponseEntity.ok(shipmentService.addShipment(shipment));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipmentDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShipmentsList(){
        return ResponseEntity.ok(shipmentService.getShipmentsList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateShipmentDetails(@RequestBody Shipment shipment){
        return ResponseEntity.ok(shipmentService.updateShipment(shipment));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShipment(@PathVariable("id") Integer id){
        shipmentService.deleteShipment(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
