package net.sarokh.api.controller;

import net.sarokh.api.model.GetBillDetailDTO;
import net.sarokh.api.model.RecordPaymentDTO;
import net.sarokh.api.model.entity.Ledger;
import net.sarokh.api.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bill")
public class BillController {

    @Autowired
    private LedgerService ledgerService;

    @RequestMapping(value = "/create-bill", method = RequestMethod.POST)
    public ResponseEntity<?> createBill(@RequestBody Ledger ledger){
        return ResponseEntity.ok(ledgerService.addLedger(ledger));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBillDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(ledgerService.getLedgerById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getBillsList(){
        return ResponseEntity.ok(ledgerService.getLedgersList());
    }

    @RequestMapping(value = "/get-bill-to-details/{userType}", method = RequestMethod.POST)
    public ResponseEntity<?> getBillToDetails(@PathVariable("userType") String userType){
        return ResponseEntity.ok(ledgerService.getLedgerForDetails(userType));
    }

    @RequestMapping(value = "/get-user-bills", method = RequestMethod.POST)
    public ResponseEntity<?> getUserUnpaidBills(@RequestBody GetBillDetailDTO billTo){
        return ResponseEntity.ok(ledgerService.getUserUnpaidBills(billTo));
    }

    @RequestMapping(value = "/record-bill-payment", method = RequestMethod.POST)
    public ResponseEntity<?> recordBillPayment(@RequestBody RecordPaymentDTO recordPaymentDTO){
        return ResponseEntity.ok(ledgerService.recordBillPayment(recordPaymentDTO));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBill(@PathVariable("id") Integer id){
        return ResponseEntity.ok(ledgerService.deleteLedger(id));
    }
}
