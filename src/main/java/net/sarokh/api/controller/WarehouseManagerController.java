package net.sarokh.api.controller;

import net.sarokh.api.model.WarehouseManagerDTO;
import net.sarokh.api.model.entity.WarehouseManager;
import net.sarokh.api.service.WarehouseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("warehouse-manager")
public class WarehouseManagerController {

    @Autowired
    private WarehouseManagerService warehouseManagerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addWarehouseManager(@RequestBody WarehouseManager warehouseManager){
        return ResponseEntity.ok(warehouseManagerService.addWarehouseManager(warehouseManager));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getWarehouseManagerDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(warehouseManagerService.getWarehouseManagerById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getWarehouseManagersList(){
        return ResponseEntity.ok(warehouseManagerService.getWarehouseManagersList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateWarehouseManagerDetails(@RequestBody WarehouseManager warehouseManager){
        return ResponseEntity.ok(warehouseManagerService.updateWarehouseManager(warehouseManager));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWarehouseManager(@PathVariable("id") Integer id){
        warehouseManagerService.deleteWarehouseManager(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
