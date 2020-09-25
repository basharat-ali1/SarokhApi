package net.sarokh.api.service;

import net.sarokh.api.dao.VehicleMaintenanceRepository;
import net.sarokh.api.model.entity.VehicleMaintenance;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleMaintenanceService {

    @Autowired
    private VehicleMaintenanceRepository repository;

    public ApiResponse addVehicleMaintenance (VehicleMaintenance vehicleMaintenance){
        return ApiResponse.builder()
                .data(repository.save(vehicleMaintenance))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getVehicleMaintenanceById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getVehicleMaintenancesList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse delete(Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data("Maintenance record successfully deleted.")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }
}
