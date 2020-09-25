package net.sarokh.api.controller;

import net.sarokh.api.model.entity.Permission;
import net.sarokh.api.model.PermissionDTO;
import net.sarokh.api.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-permissions")
public class PermissionsController {

    @Autowired
    private PermissionsService permissionsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addPermissions(@RequestBody Permission permission){
        return ResponseEntity.ok(permissionsService.addPermission(permission));
    }

    @RequestMapping(value = "/get-details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPermissionsDetails(@PathVariable("id") int id){
        return ResponseEntity.ok(permissionsService.getPermissionById(id));
    }

    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<?> getPermissionsList(){
        return ResponseEntity.ok(permissionsService.getPermissionsList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<String> updatePermissionsDetails(@RequestBody PermissionDTO permission){
        ResponseEntity<String> response = null;
        return response;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> deletePermissions(@PathVariable("id") int id){
        ResponseEntity<String> response = null;
        return response;
    }

}
