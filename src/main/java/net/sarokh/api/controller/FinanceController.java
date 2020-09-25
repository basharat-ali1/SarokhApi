package net.sarokh.api.controller;


import net.sarokh.api.model.entity.PaymentTransaction;
import net.sarokh.api.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @RequestMapping(value = "/get-finance-dashboard", method = RequestMethod.GET)
    public ResponseEntity<?> getFinanceDashboard(){
        return ResponseEntity.ok(financeService.getFinanceDashboard());
    }

    @RequestMapping(value = "/all-ledgers", method = RequestMethod.GET)
    public ResponseEntity<?> allLedgers(){
        return ResponseEntity.ok(financeService.allLedgers());
    }

    @RequestMapping(value = "/get-ledger-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLedgerDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(financeService.getLedgerDetails(id));
    }

    @RequestMapping(value = "/receive-cash-by-cashier", method = RequestMethod.POST)
    public ResponseEntity<?> receiveCashByCashier(@RequestBody PaymentTransaction paymentTransaction){
        return ResponseEntity.ok(financeService.receiveCashByCashier(paymentTransaction));
    }

    @RequestMapping(value = "/dispensing-cash/{parentRole}", method = RequestMethod.POST)
    public ResponseEntity<?> dispensingCash(@RequestBody PaymentTransaction paymentTransaction){
        return ResponseEntity.ok(financeService.dispensingCash(paymentTransaction));
    }

    @RequestMapping(value = "/get-cod-collection", method = RequestMethod.GET)
    public ResponseEntity<?> getCODCollection(){
        return ResponseEntity.ok(financeService.getCODCollection());
    }

    @RequestMapping(value = "/get-shipper-billing", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperBilling(){
        return ResponseEntity.ok(financeService.getShipperBilling());
    }

    @RequestMapping(value = "/get-driver-payout", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverPayout(){
        return ResponseEntity.ok(financeService.getDriverPayout());
    }

    @RequestMapping(value = "/get-dealer-payout", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerPayout(){
        return ResponseEntity.ok(financeService.getDealerPayout());
    }

}
