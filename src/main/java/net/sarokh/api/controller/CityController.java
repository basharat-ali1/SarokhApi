package net.sarokh.api.controller;

import net.sarokh.api.model.entity.City;
import net.sarokh.api.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("city")
public class CityController {

    @Autowired
    private CityService cityService;

   /* @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addCity(@RequestBody City city){
        return ResponseEntity.ok(cityService.add(city));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCityDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(cityService.getCityById(id));
    }*/

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getCitiesList(){
        // This will only return Saudi Arabia cities. Country code = SAU
        return ResponseEntity.ok(cityService.getCountryCitiesList("SAU"));
    }

    @RequestMapping(value = "/get-country-city/{countryCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getCountryCitiesList(@PathVariable("countryCode") String countryCode){
        return ResponseEntity.ok(cityService.getCountryCitiesList(countryCode));
    }

 /*   @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCityDetails(@RequestBody City city){
       // return ResponseEntity.ok(driverService.updateCity(driver));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCity(@PathVariable("id") Integer id){
        return ResponseEntity.ok(cityService.deleteCity(id));
    }
    */
}
