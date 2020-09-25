package net.sarokh.api.controller;

import net.sarokh.api.model.entity.CityZone;
import net.sarokh.api.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("city-zone")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addZone(@RequestBody CityZone cityZone){
        return ResponseEntity.ok(zoneService.add(cityZone));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getZoneDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getZonesList(){
        return ResponseEntity.ok(zoneService.getZonesList());
    }

 /*   @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateZoneDetails(@RequestBody Zone driver){
       // return ResponseEntity.ok(driverService.updateZone(driver));
    }
*/
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteZone(@PathVariable("id") Integer id){
        return ResponseEntity.ok(zoneService.deleteZone(id));
    }

}
