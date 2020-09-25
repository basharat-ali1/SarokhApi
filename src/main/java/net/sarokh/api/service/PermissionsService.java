package net.sarokh.api.service;

import net.sarokh.api.dao.PermissionsRepository;
import net.sarokh.api.model.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionsService {

    @Autowired
    private PermissionsRepository repository;

    public Permission addPermission(Permission permission){
        return repository.save(permission);
    }

    public Optional<Permission> getPermissionById(Integer id){
        return repository.findById(id);
    }

    public List<Permission> getPermissionsList(){
        return repository.findAll();
    }
}
