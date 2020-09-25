package net.sarokh.api.controller;

import net.sarokh.api.model.ShipperTarrifDTO;
import net.sarokh.api.model.entity.ShipperTariff;
import net.sarokh.api.service.ShipperTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ShipperTariff")
public class ShipperTariffController {

    @Autowired
    private ShipperTariffService shipperTariffService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addShipperTariff(@RequestBody ShipperTariff shipperTariff){
        return ResponseEntity.ok(shipperTariffService.addShipperTariff(shipperTariff));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperTariffDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(shipperTariffService.getShipperTariffById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperTariffsList(){
        return ResponseEntity.ok(shipperTariffService.getShipperTariffsList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<?> updateShipperTariffDetails(@RequestBody ShipperTarrifDTO shipperTariff){
        ResponseEntity<?> response = null;
        return response;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteShipperTariff(@PathVariable("id") int id){
        ResponseEntity<?> response = null;
        return response;
    }

}
