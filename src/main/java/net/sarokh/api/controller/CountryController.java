package net.sarokh.api.controller;

import net.sarokh.api.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/get-countries-list", method = RequestMethod.GET)
    public ResponseEntity<?> getCountriesList(){
        return ResponseEntity.ok(countryService.getCountriesList());
    }

}
