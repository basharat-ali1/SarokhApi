package net.sarokh.api.controller;

import net.sarokh.api.model.entity.Role;
import net.sarokh.api.model.RoleDTO;
import net.sarokh.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Role-role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addRole(@RequestBody RoleDTO role){
        return ResponseEntity.ok(roleService.addRole(role));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoleDetails(@PathVariable("id") Integer id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getRolesList(){
        return ResponseEntity.ok(roleService.getRolesList());
    }

    @RequestMapping(value = "/get-roles-by-type/{parentRole}", method = RequestMethod.GET)
    public ResponseEntity<?> getRolesByParentType(@PathVariable("parentRole") String parentRole){
        return ResponseEntity.ok(roleService.getRolesByParentType(parentRole));
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<?> updateRoleDetails(@RequestBody RoleDTO role){
        ResponseEntity<String> response = null;
        return response;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> deleteRole(@PathVariable("id") int id){
        ResponseEntity<String> response = null;
        return response;
    }

}
