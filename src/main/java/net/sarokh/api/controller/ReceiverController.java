package net.sarokh.api.controller;

import net.sarokh.api.model.entity.Receiver;
import net.sarokh.api.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("receiver")
public class ReceiverController {

    @Autowired
    private ReceiverService receiverService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addReceiver(@RequestBody Receiver receiver){
        return ResponseEntity.ok(receiverService.addReceiver(receiver));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReceiverDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(receiverService.getReceiverById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getReceiversList(){
        return ResponseEntity.ok(receiverService.getReceiversList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateReceiverDetails(@RequestBody Receiver receiver){
        return ResponseEntity.ok(receiverService.upateReceiver(receiver));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReceiver(@PathVariable("id") Integer id){
        receiverService.deleteReceiver(id);
        return ResponseEntity.ok("Id=" + id + " Successfully deleted.");
    }

}
