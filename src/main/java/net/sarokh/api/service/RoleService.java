package net.sarokh.api.service;

import net.sarokh.api.dao.RoleRepository;
import net.sarokh.api.model.RoleDTO;
import net.sarokh.api.model.entity.Role;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Role addRole(RoleDTO role){
        Role newRole = modelMapper.map(role, Role.class);
        return repository.save(newRole);
    }

    public Optional<Role> getRoleById(Integer id){
        return repository.findById(id);
    }

    public Optional<Role> getRoleByName(String name){
        return repository.getRoleByName(name);
    }

    public Iterable<Role> getRolesList() {
        return repository.findAll();
    }

    public ApiResponse getRolesByParentType(String parentRole) {

        return ApiResponse.builder()
                .data(repository.findAllByParentRole(parentRole))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }


}
