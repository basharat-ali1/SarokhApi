package net.sarokh.api.controller;

import net.sarokh.api.model.BankAccountDTO;
import net.sarokh.api.model.SmsDTo;
import net.sarokh.api.model.entity.BankAccount;
import net.sarokh.api.service.BankAccountService;
import net.sarokh.api.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("sms")
public class SendSmsController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/send-sms", method = RequestMethod.POST)
    public ResponseEntity<?> sendSms(@RequestBody SmsDTo smsDTo){
        return ResponseEntity.ok(SmsUtil.sendSMS(restTemplate,smsDTo.getCellNumber(), smsDTo.getMessage()));
    }


}
