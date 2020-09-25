package net.sarokh.api.service;

import net.sarokh.api.dao.ShipperTariffRepository;
import net.sarokh.api.model.entity.ShipperTariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipperTariffService {

    @Autowired
    private ShipperTariffRepository repository;

    public ShipperTariff addShipperTariff (ShipperTariff shipperTariff){
        return repository.save(shipperTariff);
    }

    public Optional<ShipperTariff> getShipperTariffById(Integer id){
        return repository.findById(id);
    }

    public Iterable<ShipperTariff> getShipperTariffsList() {
        return repository.findAll();
    }
}
