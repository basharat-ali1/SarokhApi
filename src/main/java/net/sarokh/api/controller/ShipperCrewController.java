package net.sarokh.api.controller;

import net.sarokh.api.model.ShipperCrewDTO;
import net.sarokh.api.model.entity.ShipperCrew;
import net.sarokh.api.service.ShipperCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipper-crew")
public class ShipperCrewController {

    @Autowired
    private ShipperCrewService shipperCrewService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addShipperCrew(@RequestBody ShipperCrew shipperCrew){
        return ResponseEntity.ok(shipperCrewService.addShipperCrew(shipperCrew));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperCrewDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(shipperCrewService.getShipperCrewById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperCrewsList(){
        return ResponseEntity.ok(shipperCrewService.getShipperCrewsList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<?> updateShipperCrewDetails(@RequestBody ShipperCrewDTO shipperCrew){
        ResponseEntity<?> response = null;
        return response;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteShipperCrew(@PathVariable("id") int id){
        ResponseEntity<?> response = null;
        return response;
    }

}
