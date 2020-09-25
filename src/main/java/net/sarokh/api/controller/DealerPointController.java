package net.sarokh.api.controller;

import net.sarokh.api.model.DealerPointDTO;
import net.sarokh.api.model.entity.DealerPoint;
import net.sarokh.api.service.DealerPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dealer-point")
public class DealerPointController {

    @Autowired
    private DealerPointService dealerPointService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDealerPoint(@RequestBody DealerPointDTO point){
        return ResponseEntity.ok(dealerPointService.add(point));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDealerPointDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(dealerPointService.getDealerPointById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getCitiesList(){
        return ResponseEntity.ok(dealerPointService.getDealerPointsList());
    }

   @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDealerPointDetails(@RequestBody DealerPointDTO point){
       return ResponseEntity.ok(dealerPointService.updateDealerPoint(point));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDealerPoint(@PathVariable("id") Integer id){
        return ResponseEntity.ok(dealerPointService.deleteDealerPoint(id));
    }

}
