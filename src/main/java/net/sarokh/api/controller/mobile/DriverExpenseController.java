package net.sarokh.api.controller.mobile;

import net.sarokh.api.model.entity.DriverExpense;
import net.sarokh.api.service.mobile.DriverExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("driver-expense")
public class DriverExpenseController {

    @Autowired
    private DriverExpenseService driverExpenseService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDriverExpense(@RequestBody DriverExpense expense){
        return ResponseEntity.ok(driverExpenseService.addDriverExpense(expense));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverExpenseDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverExpenseService.getDriverExpenseById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverExpensesList(){
        return ResponseEntity.ok(driverExpenseService.getDriverExpensesList());
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDriverExpenseDetails(@RequestBody DriverExpense expense){
        return ResponseEntity.ok(driverExpenseService.updateDriverExpense(expense));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDriverExpense(@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverExpenseService.deleteDriverExpense(id));
    }



}
