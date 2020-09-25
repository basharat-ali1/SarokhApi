package net.sarokh.api.controller;

import net.sarokh.api.model.DispatchTripDTO;
import net.sarokh.api.model.trip.CreateTripDetailsDTO;
import net.sarokh.api.service.DispatchTripService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trip")
public class DispatchTripController {

    @Autowired
    private DispatchTripService dispatchTripService;

  /*  @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addDispatchTrip(@RequestBody DispatchTrip dispatchTrip){
        return ResponseEntity.ok(dispatchTripService.addDispatchTrip(dispatchTrip));
    }
*/
    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDispatchTripDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(dispatchTripService.getDispatchTripById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getDispatchTripsList(){
        return ResponseEntity.ok(dispatchTripService.getDispatchTripsList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDispatchTripDetails(@RequestBody DispatchTripDTO dispatchTrip){
        ResponseEntity<?> response = null;
        return response;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDispatchTrip(@PathVariable("id") int id){
        return ResponseEntity.ok(dispatchTripService.deleteTrip(id));
    }

    @RequestMapping(value = "/get-trips-shipments/{warehouseId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTripShipments(@PathVariable("warehouseId") int warehouseId){
        return ResponseEntity.ok(dispatchTripService.getTripShipments(warehouseId));
    }

    @RequestMapping(value = "/create-trip", method = RequestMethod.POST)
    public ResponseEntity<?> createTrip(@RequestBody CreateTripDetailsDTO tripDetailsDTO){

        if (tripDetailsDTO == null){
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(ApplicationMessagesUtil.INVALID_TRIP_DETAIL_INFO)
                    .build());
        } else {
            if (tripDetailsDTO.getDriverId() != null) {
                if (dispatchTripService.isDriverHasActiveTrips(tripDetailsDTO.getDriverId())){
                    return ResponseEntity.ok(ApiResponse.builder()
                            .data(null)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(ApplicationMessagesUtil.ALREADY_ACTIVE_DETAIL_TRIPS)
                            .build());
                }
            }

            return ResponseEntity.ok(dispatchTripService.createTrip(tripDetailsDTO));
        }
    }

}
