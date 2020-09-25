package net.sarokh.api.service;

import net.sarokh.api.dao.DispatchTripRepository;
import net.sarokh.api.dao.OrderRepository;
import net.sarokh.api.dao.SarokhWarehouseRepository;
import net.sarokh.api.dao.ShipperRepository;
import net.sarokh.api.model.*;
import net.sarokh.api.model.admin.WarehouseInventoryDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.DispatchTripStatusEnum;
import net.sarokh.api.model.enums.PickupDeliveryEnum;
import net.sarokh.api.model.trip.AssignDriverToShipmentDTO;
import net.sarokh.api.service.mobile.MobileDealerService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class SarokhWarehouseService {

    @Autowired
    private SarokhWarehouseRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DispatchTripRepository tripRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private MobileDealerService mobileDealerService;

    public SarokhWarehouse addSarokhWarehouse (SarokhWarehouse sarokhWarehouse){
        return repository.save(sarokhWarehouse);
    }

    public Optional<SarokhWarehouse> getSarokhWarehouseById(Integer id){
        return repository.findById(id);
    }

    public ApiResponse getSarokhWarehousesList() {
        Iterable<SarokhWarehouse> warehouseList = repository.findAll();

        List<MapLocationDTO> mapLocations = new ArrayList<>();
        Iterator<SarokhWarehouse> iterator = warehouseList.iterator();
        while (iterator.hasNext()){
            SarokhWarehouse warehouse = iterator.next();
            mapLocations.add(MapLocationDTO.builder()
                    .label(warehouse.getName())
                    .latitude(warehouse.getLocationLatitude())
                    .longitude(warehouse.getLocationLongitude())
                    .build());
        }

        SarokhWarehouseListDTO warehouses = SarokhWarehouseListDTO.builder()
                .warehouseList(warehouseList)
                .mapLocations(mapLocations)
                .build();

        return ApiResponse.builder()
                .data(warehouses)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();

    }

    public SarokhWarehouse updateSarokhWarehouse (SarokhWarehouse sarokhWarehouse){
        return repository.save(sarokhWarehouse);
    }

    public ApiResponse deleteSarokhWarehouse(Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data("Warehouse Successfully deleted.")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse assignCardToShipment(AssignCardToShipmentDTO assignCardToShipmentDTO){
        if (orderService.assignCardToShipment(assignCardToShipmentDTO)) {
            Optional<ShipmentOrder> order = orderRepository.findByOrderId(assignCardToShipmentDTO.getTrackingNumber());
            ShipmentOrderItem orderItem = order.get().getShipmentOrderItems() != null && order.get().getShipmentOrderItems().size() > 0 ? order.get().getShipmentOrderItems().get(0) : null;
            Optional<Shipper> shipper = shipperRepository.findById(order.get().getShipperId());
            return ApiResponse.builder()
                    .data(WarehouseTerminalDTO.builder()
                            .trackingNumber(order.get().getOrderId())
                            .fromCity(order.get().getShipFromCity())
                            .toCity(order.get().getShipToCity())
                            .shipper(shipper.isPresent() ? shipper.get().getFirstName() + " " + shipper.get().getLastName() : null)
                            .address(orderItem.getAddress())
                            .build())
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.CARD_SUCCESSFULLY_ASSIGNED)
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ApplicationMessagesUtil.INVALID_ORDER_OR_CARD_NUMBER)
                .build();
    }

    public ApiResponse assignDriverToShipment(AssignDriverToShipmentDTO assignDriverToShipmentDTO){
        if (orderService.assignDriverToShipment(assignDriverToShipmentDTO)){
            Optional<ShipmentOrder> order = orderRepository.findByOrderId(assignDriverToShipmentDTO.getTrackingNumber());
            ShipmentOrderItem orderItem = order.get().getShipmentOrderItems() != null && order.get().getShipmentOrderItems().size() > 0 ? order.get().getShipmentOrderItems().get(0) : null;
            Optional<Shipper> shipper = shipperRepository.findById(order.get().getShipperId());
            return ApiResponse.builder()
                    .data(WarehouseTerminalDTO.builder()
                            .trackingNumber(order.get().getOrderId())
                            .fromCity(order.get().getShipFromCity())
                            .toCity(order.get().getShipToCity())
                            .shipper(shipper.isPresent() ? shipper.get().getFirstName() + " " + shipper.get().getLastName() : null)
                            .address(orderItem.getAddress())
                            .build())
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.DRIVER_SUCCESSFULLY_ASSIGNED)
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ApplicationMessagesUtil.INVALID_ORDER_OR_DRIVER_INFO)
                .build();
    }

    public ApiResponse getSarokhWarehouseDashboard(Integer warehouseId){

        Optional<SarokhWarehouse> warehouse = repository.findById(warehouseId);

        List<WarehouseShipmentDTO> list = new ArrayList<>();

        if (warehouse.isPresent()) {
            List<ShipmentOrder> shipmentsList = orderService.findShipmentsByAssignedToWarehouse(warehouseId);

            String itemsList = "";
            for(ShipmentOrder order : shipmentsList) {
                itemsList += order.getOrderId() + ",";

                list.add(WarehouseShipmentDTO.builder()
                        .trackingNumber(order.getOrderId())
                        .pickupType(order.getPickupLocation())
                        .deliveryType(order.getDeliveryLocation())
                        .receiverName(order.getShipmentOrderItems().get(0).getReceiverName())
                        .receiverContact(order.getShipmentOrderItems().get(0).getContact())
                        .shipper(order.getShipperId())
                        .paymentType(order.getShipmentOrderItems().get(0).getPaymentType())
                        .status(order.getStatus())
                        .build());

            }

            int aboutToReceiveShipments = 0;
            int deliveriesDueShipments = 0;

            if (itemsList.length() > 0) {
                Iterable<DispatchTrip> trips = tripRepository.findAllByTripStatus(DispatchTripStatusEnum.Active.name());
                Iterator<DispatchTrip> tripIterator = trips.iterator();

                while (tripIterator.hasNext()) {
                    DispatchTrip trip = tripIterator.next();

                    for (DispatchTripDetail detail : trip.getTripDetailItemsList()) {
                        if (itemsList.indexOf(detail.getShipmentOrderId()) >= 0){
                            if (detail.getPickupDelivery().equals(PickupDeliveryEnum.Pickup.name())){
                                aboutToReceiveShipments ++;
                            } else {
                                deliveriesDueShipments ++;
                            }
                        }
                    }
                }
            }

            return ApiResponse.builder()
                    .data(WarehouseDashboardDTO.builder()
                            .locationLatitude(warehouse.get().getLocationLatitude())
                            .locationLongitude(warehouse.get().getLocationLongitude())
                            .shipments(list)
                            .deliveriesDue(deliveriesDueShipments)
                            .totalShipments(list.size())
                            .shipmentsAboutToReceive(aboutToReceiveShipments)
                            .build())
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build();
        }

        return ApiResponse.builder()
                .data(WarehouseDashboardDTO.builder()
                        .locationLatitude(null)
                        .locationLongitude(null)
                        .shipments(null)
                        .deliveriesDue(0)
                        .totalShipments(0)
                        .shipmentsAboutToReceive(0)
                        .build())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();

    }

    // TODO: Mock Data returned. Will be changed
    public ApiResponse getWarehouseInventoryManagement(){
        List<WarehouseInventoryDTO> list = new ArrayList<>();
        list.add(WarehouseInventoryDTO.builder()
                .warehouseName("Warehouse 1")
                .timeDate("25-02-25")
                .usage(1000)
                .totalInventory(800)
                .newDelivery(300)
                .reAttempt(20)
                .returnInv(10)
                .build());
        list.add(WarehouseInventoryDTO.builder()
                .warehouseName("Warehouse 1")
                .timeDate("25-02-25")
                .usage(1000)
                .totalInventory(800)
                .newDelivery(300)
                .reAttempt(20)
                .returnInv(10)
                .build());
        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    
}
