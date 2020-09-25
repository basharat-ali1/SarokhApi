package net.sarokh.api.controller;

import net.sarokh.api.model.BankAccountDTO;
import net.sarokh.api.model.entity.BankAccount;
import net.sarokh.api.service.BankAccountService;
import net.sarokh.api.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("account")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addBankAccount(@RequestBody BankAccountDTO bankAccount){
        return ResponseEntity.ok(bankAccountService.addBankAccount(bankAccount));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBankAccountDetails(@PathVariable("id") Integer id){
        ResponseEntity<String> response = null;
        return response;
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getBankAccountsList(){
        ResponseEntity<String> response = null;
        return response;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBankAccountDetails(@RequestBody BankAccount bankAccount){
        return ResponseEntity.ok(bankAccountService.updateBankAccount(bankAccount));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBankAccount(@PathVariable("id") Integer id){
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
