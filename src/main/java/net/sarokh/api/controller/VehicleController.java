package net.sarokh.api.controller;

import net.sarokh.api.model.VehicleDTO;
import net.sarokh.api.model.entity.Vehicle;
import net.sarokh.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle){
        return ResponseEntity.ok(vehicleService.addVehicle(vehicle));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getVehicleDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getVehiclesList(){
        return ResponseEntity.ok(vehicleService.getVehiclesList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateVehicleDetails(@RequestBody Vehicle vehicle){
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicle));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleService.deleteVehicle(id));
    }

}
