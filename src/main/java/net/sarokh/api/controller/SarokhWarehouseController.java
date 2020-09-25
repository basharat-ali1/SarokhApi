package net.sarokh.api.controller;

import net.sarokh.api.model.AssignCardToShipmentDTO;
import net.sarokh.api.model.entity.SarokhWarehouse;
import net.sarokh.api.model.trip.AssignDriverToShipmentDTO;
import net.sarokh.api.service.OrderService;
import net.sarokh.api.service.SarokhWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sarokh-warehouse")
public class SarokhWarehouseController {

    @Autowired
    private SarokhWarehouseService sarokhWarehouseService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addSarokhWarehouse(@RequestBody SarokhWarehouse sarokhWarehouse){
        return ResponseEntity.ok(sarokhWarehouseService.addSarokhWarehouse(sarokhWarehouse));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSarokhWarehouseDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(sarokhWarehouseService.getSarokhWarehouseById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getSarokhWarehousesList(){
        return ResponseEntity.ok(sarokhWarehouseService.getSarokhWarehousesList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSarokhWarehouseDetails(@RequestBody SarokhWarehouse sarokhWarehouse){
        return ResponseEntity.ok(sarokhWarehouseService.updateSarokhWarehouse(sarokhWarehouse));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSarokhWarehouse(@PathVariable("id") Integer id){
        return ResponseEntity.ok(sarokhWarehouseService.deleteSarokhWarehouse(id));
    }


    @RequestMapping(value = "/get-warehouse-dashboard/{warehouseId}", method = RequestMethod.GET)
    public ResponseEntity<?> getSarokhWarehouseDashboard(@PathVariable("warehouseId") Integer warehouseId){
        return ResponseEntity.ok(sarokhWarehouseService.getSarokhWarehouseDashboard(warehouseId));
    }

    @RequestMapping(value = "/assign-card-to-shipment", method = RequestMethod.POST)
    public ResponseEntity<?> assignCardToShipment(@RequestBody AssignCardToShipmentDTO assignCardToShipmentDTO){
        return ResponseEntity.ok(sarokhWarehouseService.assignCardToShipment(assignCardToShipmentDTO));
    }

    @RequestMapping(value = "/assign-driver-to-shipment", method = RequestMethod.POST)
    public ResponseEntity<?> assignDriverToShipment(@RequestBody AssignDriverToShipmentDTO assignDriverToShipmentDTO){
        return ResponseEntity.ok(sarokhWarehouseService.assignDriverToShipment(assignDriverToShipmentDTO));
    }

}
