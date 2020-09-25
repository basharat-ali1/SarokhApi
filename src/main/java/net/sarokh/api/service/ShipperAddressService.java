package net.sarokh.api.service;

import net.sarokh.api.dao.ShipperAddressRepository;
import net.sarokh.api.model.entity.ShipperAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipperAddressService {

    @Autowired
    private ShipperAddressRepository repository;

    public ShipperAddress addShipperAddress (ShipperAddress shipperAddress){
        return repository.save(shipperAddress);
    }

    public Optional<ShipperAddress> getShipperAddressById(Integer id){
        return repository.findById(id);
    }

    public Iterable<ShipperAddress> getShipperAddresssList() {
        return repository.findAll();
    }
}
