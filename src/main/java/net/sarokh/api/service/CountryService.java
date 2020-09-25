package net.sarokh.api.service;

import net.sarokh.api.dao.CountryRepository;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public ApiResponse getCountriesList(){
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


}
