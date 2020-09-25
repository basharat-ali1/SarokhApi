package net.sarokh.api.service;

import net.sarokh.api.dao.ShippingInfoRepository;
import net.sarokh.api.model.entity.ShippingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShippingInfoService {

    @Autowired
    private ShippingInfoRepository repository;

    public ShippingInfo addShippingInfo (ShippingInfo shippingInfo){
        return repository.save(shippingInfo);
    }

    public Optional<ShippingInfo> getShippingInfoById(Integer id){
        return repository.findById(id);
    }

    public Iterable<ShippingInfo> getShippingInfosList() {
        return repository.findAll();
    }

    public ShippingInfo updateShippingInfo (ShippingInfo shippingInfo){
        return repository.save(shippingInfo);
    }

    public void deleteShippingInfo (Integer id){
        repository.deleteById(id);
    }
}
