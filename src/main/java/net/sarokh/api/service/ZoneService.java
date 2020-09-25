package net.sarokh.api.service;

import net.sarokh.api.dao.ZoneRepository;
import net.sarokh.api.model.entity.CityZone;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository repository;

    public ApiResponse add(CityZone cityZone){
        return ApiResponse.builder()
                .data(repository.save(cityZone))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.ZONE_SUCCESSFULLY_CREATED)
                .build();
    }

    public ApiResponse getZoneById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse getZonesList() {

        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse deleteZone(Integer id) {
        repository.deleteById(id);
        return ApiResponse.builder()
                .data(id + "successfully deleted")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

}
