package net.sarokh.api.controller;

import net.sarokh.api.model.entity.City;
import net.sarokh.api.model.entity.ShipmentIssueReport;
import net.sarokh.api.service.CityService;
import net.sarokh.api.service.ShipmentIssueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shipment-issue")
public class ShipmentIssueReportController {

    @Autowired
    private ShipmentIssueReportService issueReportService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addIssueReport(@RequestBody ShipmentIssueReport report){
        return ResponseEntity.ok(issueReportService.add(report));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getIssueReportDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(issueReportService.getIssueReportById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getIssueReportsList(){
        return ResponseEntity.ok(issueReportService.getIssueReportsList());
    }

 /*   @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateIssueReportDetails(@RequestBody ShipmentIssueReport report){
       // return ResponseEntity.ok(issueReportService.updateShipmentIssueReport(driver));
    }
*/
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteIssueReport(@PathVariable("id") Integer id){
        return ResponseEntity.ok(issueReportService.deleteIssueReport(id));
    }

}
