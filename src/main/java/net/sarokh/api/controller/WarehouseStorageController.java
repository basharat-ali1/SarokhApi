package net.sarokh.api.controller;

import net.sarokh.api.model.WarehouseStorageDTO;
import net.sarokh.api.model.entity.WarehouseStorage;
import net.sarokh.api.service.WarehouseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("warehouse-storage")
public class WarehouseStorageController {

    @Autowired
    private WarehouseStorageService warehouseStorageService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addWarehouseStorage(@RequestBody WarehouseStorage warehouseStorage){
        return ResponseEntity.ok(warehouseStorageService.addWarehouseStorage(warehouseStorage));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getWarehouseStorageDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(warehouseStorageService.getWarehouseStorageById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getWarehouseStoragesList(){
        return ResponseEntity.ok(warehouseStorageService.getWarehouseStoragesList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateWarehouseStorageDetails(@RequestBody WarehouseStorage warehouseStorage){
        return ResponseEntity.ok(warehouseStorageService.updateWarehouseStorage(warehouseStorage));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWarehouseStorage(@PathVariable("id") Integer id){
        warehouseStorageService.deleteWarehouseStorage(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
