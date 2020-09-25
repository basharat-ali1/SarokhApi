package net.sarokh.api.controller;

import net.sarokh.api.model.OrderPickupTypeDTO;
import net.sarokh.api.model.entity.OrderPickupType;
import net.sarokh.api.service.OrderPickupTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pickup-type")
public class OrderPickupTypeController {

    @Autowired
    private OrderPickupTypeService orderPickupTypeService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addPickupType(@RequestBody OrderPickupType orderPickupType){
        return ResponseEntity.ok(orderPickupTypeService.addOrderPickupType(orderPickupType));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPickupTypeDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(orderPickupTypeService.getOrderPickupTypeById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getPickupTypesList(){
        return ResponseEntity.ok(orderPickupTypeService.getOrderPickupTypesList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePickupTypeDetails(@RequestBody OrderPickupType orderPickupType){
        return ResponseEntity.ok(orderPickupTypeService.updateOrderPickupType(orderPickupType));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePickupType(@PathVariable("id") Integer id){
        orderPickupTypeService.deleteOrderPickupType(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
