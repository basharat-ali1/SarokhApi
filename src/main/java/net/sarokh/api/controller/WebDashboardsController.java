package net.sarokh.api.controller;

import net.sarokh.api.service.WebDashboardsService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web-dashboard")
public class WebDashboardsController {

    @Autowired
    private WebDashboardsService dashboardsService;

    @RequestMapping(value = "/admin/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAdminDashboard(@PathVariable("id") Integer id){

        if (null == id || 0 == id){
            return ResponseEntity.ok(ApiResponse.builder()
                    .message(ApplicationMessagesUtil.INVALID_USER_DETAILS)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build());
        }

        return ResponseEntity.ok(dashboardsService.loadAdminDashboard(id));

    }

    @RequestMapping(value = "/shipper/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShipperDashboard(@PathVariable("id") Integer id){

        if (null == id || 0 == id){
            return ResponseEntity.ok(ApiResponse.builder()
                    .message(ApplicationMessagesUtil.INVALID_USER_DETAILS)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build());
        }

        return ResponseEntity.ok(dashboardsService.loadShipperDashboard(id));

    }
}
