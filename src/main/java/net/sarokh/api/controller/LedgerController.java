package net.sarokh.api.controller;

import net.sarokh.api.model.entity.Ledger;
import net.sarokh.api.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addLedger(@RequestBody Ledger ledger){
        return ResponseEntity.ok(ledgerService.addLedger(ledger));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLedgerDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(ledgerService.getLedgerById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getLedgersList(){
        return ResponseEntity.ok(ledgerService.getLedgersList());
    }

    @RequestMapping(value = "/get-shipper-ledgers/{shipperId}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperLedgersList(@PathVariable("shipperId") Integer shipperId){
        return ResponseEntity.ok(ledgerService.getShipperLedgersList(shipperId));
    }

    @RequestMapping(value = "/get-unpaid-ledgers", method = RequestMethod.GET)
    public ResponseEntity<?> getUnpaidLedgersList(){
        return ResponseEntity.ok(ledgerService.getUnPaidLedgers());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLedgerDetails(@RequestBody Ledger ledger){
        return ResponseEntity.ok(ledgerService.updateLedger(ledger));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLedger(@PathVariable("id") Integer id){
        return ResponseEntity.ok(ledgerService.deleteLedger(id));
    }

    @RequestMapping(value = "/get-ledger-for-details/{ledgerFor}", method = RequestMethod.GET)
    public ResponseEntity<?> getLedgersForDetails(@PathVariable("ledgerFor") String ledgerFor){
        return ResponseEntity.ok(ledgerService.getLedgerForDetails(ledgerFor));
    }

}
