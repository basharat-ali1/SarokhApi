package net.sarokh.api.service;

import net.sarokh.api.dao.ShipmentRepository;
import net.sarokh.api.model.entity.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository repository;

    public Shipment addShipment (Shipment shipment){
        return repository.save(shipment);
    }

    public Optional<Shipment> getShipmentById(Integer id){
        return repository.findById(id);
    }

    public Iterable<Shipment> getShipmentsList() {
        return repository.findAll();
    }

    public Shipment updateShipment (Shipment shipment){
        return repository.save(shipment);
    }

    public void deleteShipment (Integer id){
        repository.deleteById(id);
    }
}
