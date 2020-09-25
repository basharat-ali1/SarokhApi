package net.sarokh.api.service;

import net.sarokh.api.dao.WarehouseStorageRepository;
import net.sarokh.api.model.entity.WarehouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseStorageService {

    @Autowired
    private WarehouseStorageRepository repository;

    public WarehouseStorage addWarehouseStorage (WarehouseStorage warehouseStorage){
        return repository.save(warehouseStorage);
    }

    public Optional<WarehouseStorage> getWarehouseStorageById(Integer id){
        return repository.findById(id);
    }

    public Iterable<WarehouseStorage> getWarehouseStoragesList() {
        return repository.findAll();
    }

    public WarehouseStorage updateWarehouseStorage (WarehouseStorage warehouseStorage){
        return repository.save(warehouseStorage);
    }

    public void deleteWarehouseStorage (Integer id){
        repository.deleteById(id);
    }
}
