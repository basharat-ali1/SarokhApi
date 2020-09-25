package net.sarokh.api.service;

import net.sarokh.api.dao.OrderRepository;
import net.sarokh.api.dao.ShipmentOrderItemRepository;
import net.sarokh.api.model.AdminDashboardDTO;
import net.sarokh.api.model.ShipperDashboardDTO;
import net.sarokh.api.model.entity.ShipmentOrder;
import net.sarokh.api.model.entity.User;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class WebDashboardsService {

    @Autowired
    private OrderRepository shipmentOrder;

    @Autowired
    private UserService userService;

    public ApiResponse loadAdminDashboard(Integer id){

        AdminDashboardDTO dashboardDTO = AdminDashboardDTO.builder()
                .agentsPayable(230.0)
                .agentsReceivable(10.0)
                .codPayable(500.0)
                .driverPayable(63.0)
                .driverReceivable(45.0)
                .inProgressOrders(5)
                .pendingDelivery(6)
                .pendingPickups(41)
                .prepaidOrdersReceivable(32.0)
                .totalOrders(600)
                .walletPickups(70.5)
                .build();

        return ApiResponse.builder()
                .data(dashboardDTO)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse loadShipperDashboard(Integer shipperId){

        Iterable<?> result = shipmentOrder.loadShipperDashboard(shipperId);

        ShipperDashboardDTO dashboardDTO = null;


        Iterator itr = result.iterator();
        while (itr.hasNext()){
            Object[] dashboardValue = (Object[]) itr.next();

            dashboardDTO = ShipperDashboardDTO.builder()
                    .totalEarning(dashboardValue[0] != null ? dashboardValue[0] : 0)
                    .completedOrders(dashboardValue[1] != null ? dashboardValue[1] : 0)
                    .totalOrders(dashboardValue[3] != null ? dashboardValue[3] : 0)
                    .pendingOrders(dashboardValue[4] != null ? dashboardValue[4] : 0)
                    .returnOrders(dashboardValue[5] != null ? dashboardValue[5] : 0)
                    .activeShipmentIssues(dashboardValue[6] != null ? dashboardValue[6] : 0)
                    .ordersPendingDeliveries(0)
                    .pendingDeliveryCharges(0)
                    .pendingCOD(dashboardValue[2] != null ? dashboardValue[2] : 0)
                    .usersList(getShipperUsersList(shipperId))
                    .build();
        }

        return ApiResponse.builder()
                .data(dashboardDTO)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    private List<String> getShipperUsersList(Integer shipperId){
        Iterable<User> userList = userService.getShipperUsersList(shipperId);
        List<String> users = new ArrayList<>();

        userList.forEach(user -> {
            users.add(user.getFullName());
        });

        return users;
    }
}
