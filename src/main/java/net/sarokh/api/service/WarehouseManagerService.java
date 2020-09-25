package net.sarokh.api.service;

import net.sarokh.api.dao.WarehouseManagerRepository;
import net.sarokh.api.model.entity.WarehouseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseManagerService {

    @Autowired
    private WarehouseManagerRepository repository;

    public WarehouseManager addWarehouseManager (WarehouseManager warehouseManager){
        return repository.save(warehouseManager);
    }

    public Optional<WarehouseManager> getWarehouseManagerById(Integer id){
        return repository.findById(id);
    }

    public Iterable<WarehouseManager> getWarehouseManagersList() {
        return repository.findAll();
    }

    public WarehouseManager updateWarehouseManager (WarehouseManager warehouseManager){
        return repository.save(warehouseManager);
    }

    public void deleteWarehouseManager (Integer id){
        repository.deleteById(id);
    }
}
