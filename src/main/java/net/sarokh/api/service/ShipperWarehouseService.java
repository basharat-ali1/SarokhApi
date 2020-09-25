package net.sarokh.api.service;

import net.sarokh.api.dao.ShipperWarehouseRepository;
import net.sarokh.api.model.MapLocationDTO;
import net.sarokh.api.model.UserDTO;
import net.sarokh.api.model.entity.ShipperWarehouse;
import net.sarokh.api.model.shipper.ShipperWarehouseDTO;
import net.sarokh.api.model.shipper.ShipperWarehouseListDTO;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ShipperWarehouseService {

    @Autowired
    private ShipperWarehouseRepository repository;

    public ShipperWarehouse addShipperWarehouse (ShipperWarehouse shipperWarehouse){
        return repository.save(shipperWarehouse);
    }

    public Optional<ShipperWarehouse> getShipperWarehouseById(Integer id){
        return repository.findById(id);
    }

    public ApiResponse getShipperWarehousesList() {
        Iterable<ShipperWarehouse> warehouseList = repository.findAll();

        return ApiResponse.builder()
                .data(warehouseList)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getShipperWarehousesListByShipperId(Integer shipperId) {
        Iterable<ShipperWarehouse> warehouseList = repository.findAllByShipperId(shipperId);

        List<MapLocationDTO> mapLocations = new ArrayList<>();
        Iterator<ShipperWarehouse> iterator = warehouseList.iterator();
        while (iterator.hasNext()){
            ShipperWarehouse warehouse = iterator.next();
            mapLocations.add(MapLocationDTO.builder()
                    .label(warehouse.getName())
                    .latitude(warehouse.getLocationLatitude())
                    .longitude(warehouse.getLocationLongitude())
                    .build());
        }

        ShipperWarehouseListDTO warehouses = ShipperWarehouseListDTO.builder()
                .warehouseList(warehouseList)
                .mapLocations(mapLocations)
                .build();

        return ApiResponse.builder()
                .data(warehouses)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ShipperWarehouse updateShipperWarehouse (ShipperWarehouse shipperWarehouse){
        return repository.save(shipperWarehouse);
    }

    public ApiResponse deleteShipperWarehouse (Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data("Warehouse Successfully deleted.")
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }
}
