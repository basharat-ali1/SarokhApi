package net.sarokh.api.controller;

import net.sarokh.api.model.DealerDTO;
import net.sarokh.api.model.dealer.DealerFormDTO;
import net.sarokh.api.model.entity.Dealer;
import net.sarokh.api.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dealer")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDealer(@RequestBody DealerFormDTO dealerForm){
        return ResponseEntity.ok(dealerService.addDealer(dealerForm));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(dealerService.getDealerById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getDealersList(){
        return ResponseEntity.ok(dealerService.getDealersList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDealerDetails(@RequestBody DealerFormDTO dealerForm){
            return ResponseEntity.ok(dealerService.updateDealer(dealerForm));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDealer(@PathVariable("id") Integer id){
        return ResponseEntity.ok(dealerService.deleteDealer(id));
    }

    @RequestMapping(value = "/get-dealer-inventory/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerInventory(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(dealerService.getDealerInventory(dealerId));
    }

    @RequestMapping(value = "/get-delear-service-charges/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerServiceCharges(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(dealerService.getDealerServiceCharges(dealerId));
    }

    @RequestMapping(value = "/get-dealer-cod-returns/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerCODReturns(@PathVariable("dealerId") Integer dealerId){
        return ResponseEntity.ok(dealerService.getDealerCODReturns(dealerId));
    }

}
