package net.sarokh.api.controller;

import net.sarokh.api.model.PaymentTransactionDTO;
import net.sarokh.api.model.entity.PaymentTransaction;
import net.sarokh.api.service.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transaction")
public class PaymentTransactionController {

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addPaymentTransaction(@RequestBody PaymentTransaction paymentTransaction){
        return ResponseEntity.ok(paymentTransactionService.addPaymentTransaction(paymentTransaction));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentTransactionDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(paymentTransactionService.getPaymentTransactionById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentTransactionsList(){
        return ResponseEntity.ok(paymentTransactionService.getPaymentTransactionsList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePaymentTransactionDetails(@RequestBody PaymentTransaction paymentTransaction){
        return ResponseEntity.ok(paymentTransactionService.updatePaymentTransaction(paymentTransaction));
    }
/*
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePaymentTransaction(@PathVariable("id") Integer id){
        return ResponseEntity.ok(paymentTransactionService.deletePaymentTransaction(id));
    }
*/
}
