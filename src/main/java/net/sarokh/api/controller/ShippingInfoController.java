package net.sarokh.api.controller;

import net.sarokh.api.model.ShippingInfoDTO;
import net.sarokh.api.model.entity.ShippingInfo;
import net.sarokh.api.service.ShippingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipping-info")
public class ShippingInfoController {

    @Autowired
    private ShippingInfoService shippingInfoService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addShippingInfo(@RequestBody ShippingInfo shippingInfo){
        return ResponseEntity.ok(shippingInfoService.addShippingInfo(shippingInfo));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShippingInfoDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(shippingInfoService.getShippingInfoById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getShippingInfosList(){
        return ResponseEntity.ok(shippingInfoService.getShippingInfosList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateShippingInfoDetails(@RequestBody ShippingInfo shippingInfo){
        return ResponseEntity.ok(shippingInfoService.updateShippingInfo(shippingInfo));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShippingInfo(@PathVariable("id") Integer id){
        shippingInfoService.deleteShippingInfo(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
