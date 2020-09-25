package net.sarokh.api.service;

import net.sarokh.api.dao.OrderPickupTypeRepository;
import net.sarokh.api.model.entity.OrderPickupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderPickupTypeService {

    @Autowired
    private OrderPickupTypeRepository repository;

    public OrderPickupType addOrderPickupType (OrderPickupType orderPickupType){
        return repository.save(orderPickupType);
    }

    public Optional<OrderPickupType> getOrderPickupTypeById(Integer id){
        return repository.findById(id);
    }

    public Iterable<OrderPickupType> getOrderPickupTypesList() {
        return repository.findAll();
    }

    public OrderPickupType updateOrderPickupType (OrderPickupType orderPickupType){
        return repository.save(orderPickupType);
    }

    public void deleteOrderPickupType (Integer id){
        repository.deleteById(id);
    }
}
