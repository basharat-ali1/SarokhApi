package net.sarokh.api.service;

import net.sarokh.api.dao.ShipperCrewRepository;
import net.sarokh.api.model.entity.ShipperCrew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipperCrewService {

    @Autowired
    private ShipperCrewRepository repository;

    public ShipperCrew addShipperCrew (ShipperCrew shipperCrew){
        return repository.save(shipperCrew);
    }

    public Optional<ShipperCrew> getShipperCrewById(Integer id){
        return repository.findById(id);
    }

    public Iterable<ShipperCrew> getShipperCrewsList() {
        return repository.findAll();
    }
}
