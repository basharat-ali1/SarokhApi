package net.sarokh.api.controller;

import net.sarokh.api.model.VehicleMaintenanceDTO;
import net.sarokh.api.model.entity.VehicleMaintenance;
import net.sarokh.api.service.VehicleMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vehicle-maintenance")
public class VehicleMaintenanceController {

    @Autowired
    private VehicleMaintenanceService vehicleMaintenanceService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addVehicleMaintenance(@RequestBody VehicleMaintenance vehicleMaintenance){
        return ResponseEntity.ok(vehicleMaintenanceService.addVehicleMaintenance(vehicleMaintenance));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getVehicleMaintenanceDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleMaintenanceService.getVehicleMaintenanceById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getVehicleMaintenancesList(){
        return ResponseEntity.ok(vehicleMaintenanceService.getVehicleMaintenancesList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateVehicleMaintenanceDetails(@RequestBody VehicleMaintenanceDTO vehicleMaintenance){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVehicleMaintenance(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleMaintenanceService.delete(id));
    }

}
