package net.sarokh.api.controller;

import net.sarokh.api.model.GetBillDetailDTO;
import net.sarokh.api.model.WalletDTO;
import net.sarokh.api.model.entity.Wallet;
import net.sarokh.api.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addWallet(@RequestBody Wallet wallet){
        return ResponseEntity.ok(walletService.addWallet(wallet));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getWalletDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(walletService.getWalletById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getWalletsList(){
        return ResponseEntity.ok(walletService.getWalletsList());
    }

    @RequestMapping(value = "/get-user-wallets", method = RequestMethod.POST)
    public ResponseEntity<?> getUserWalletsList(@RequestBody GetBillDetailDTO billToDetailDTO){
        return ResponseEntity.ok(walletService.getUserWalletsList(billToDetailDTO));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateWalletDetails(@RequestBody Wallet wallet){
        return ResponseEntity.ok(walletService.updateWallet(wallet));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWallet(@PathVariable("id") Integer id){
        walletService.deleteWallet(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
