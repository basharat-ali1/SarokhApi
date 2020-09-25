package net.sarokh.api.controller;

import net.sarokh.api.model.driver.DriverFormDTO;
import net.sarokh.api.model.entity.Driver;
import net.sarokh.api.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDriver(@RequestBody DriverFormDTO driver){
        return ResponseEntity.ok(driverService.addDriver(driver));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getDriversList(){
        return ResponseEntity.ok(driverService.getDriversList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDriverDetails(@RequestBody DriverFormDTO driver){
        return ResponseEntity.ok(driverService.updateDriver(driver));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDriver(@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.deleteDriver(id));
    }

}
