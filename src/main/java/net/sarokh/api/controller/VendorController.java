package net.sarokh.api.controller;

import net.sarokh.api.model.VendorDTO;
import net.sarokh.api.model.dealer.DealerFormDTO;
import net.sarokh.api.model.entity.Vendor;
import net.sarokh.api.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addVendor(@RequestBody VendorDTO vendor){
        return ResponseEntity.ok(vendorService.add(vendor));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getVendorDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getVendorsList(){
        return ResponseEntity.ok(vendorService.getVendorsList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDealerDetails(@RequestBody VendorDTO vendor){
        return ResponseEntity.ok(vendorService.updateVendor(vendor));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVendor(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vendorService.deleteVendor(id));
    }
}
