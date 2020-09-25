package net.sarokh.api.service;

import net.sarokh.api.dao.CityRepository;
import net.sarokh.api.dao.RoleRepository;
import net.sarokh.api.model.RoleDTO;
import net.sarokh.api.model.entity.City;
import net.sarokh.api.model.entity.Role;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public ApiResponse add(City city){
        return ApiResponse.builder()
                .data(repository.save(city))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.CITY_SUCCESSFULLY_CREATED)
                .build();
    }

    public ApiResponse getCityById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse getCitiesList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getCountryCitiesList(String countryCode) {
        return ApiResponse.builder()
                .data(repository.findCitiesByCountryCode(countryCode))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse deleteCity(Integer id) {
        repository.deleteById(id);
        return ApiResponse.builder()
                .data(id + "successfully deleted")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

}
