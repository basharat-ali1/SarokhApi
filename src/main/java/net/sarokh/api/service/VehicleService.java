package net.sarokh.api.service;

import net.sarokh.api.dao.VehicleRepository;
import net.sarokh.api.model.entity.Vehicle;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public ApiResponse addVehicle (Vehicle vehicle){
        return ApiResponse.builder()
                .data(repository.save(vehicle))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.VEHICLE_SUCCESSFULLY_CREATED)
                .build();
    }

    public ApiResponse getVehicleById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getVehiclesList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse updateVehicle (Vehicle vehicle){
        return ApiResponse.builder()
                .data(repository.save(vehicle))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.VEHICLE_SUCCESSFULLY_UPDATED)
                .build();
    }

    public ApiResponse deleteVehicle (Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data("Vehicle id=" + id + " successfully deleted.")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

}
