package net.sarokh.api.service.mobile;

import net.sarokh.api.dao.ShipmentRepository;
import net.sarokh.api.model.DriverDTO;
import net.sarokh.api.model.OrderStatusInputDTO;
import net.sarokh.api.model.ShipmentOrderDTO;
import net.sarokh.api.model.entity.Shipment;
import net.sarokh.api.model.entity.ShipmentOrder;
import net.sarokh.api.model.mobile.*;
import net.sarokh.api.service.OrderService;
import net.sarokh.api.service.ShipmentService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MobileShipmentService {

    @Autowired
    private ShipmentRepository repository;

    @Autowired
    private OrderService orderService;

    public ApiResponse getShipmentByTrackingNumber(String trackingNumber){
        orderService.getMobileOrdersList();
        if (null == trackingNumber){
            return ApiResponse.builder()
                    .message(ApplicationMessagesUtil.INVALID_TRACKING_NUMBER)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        } else{
            ShipmentOrder order = orderService.getOrderByOrderId(trackingNumber);
            if (null != order) {
                ShipmentOrderDTO mobileShipmentDTO = ShipmentOrderDTO.builder()
                        .id(order.getId())
                        .trackingNo(order.getOrderId())
                        .shipmentNo(order.getOrderId())
                        //.orderType(order.getPickupType())
                        .pickupDatetime(order.getPickupDatetime().toString())
                        .shipperId(order.getShipperId())
                       // .receiverName(order.getShipmentOrderItems().get(0).getReceiverName())
                       // .deliveryCharges(order.getShipmentOrderItems().get(0).getShipmentDeliveryCharges())
                        .shipmentReceived(false)
                        .build();

                return ApiResponse.builder()
                        .data(mobileShipmentDTO)
                        .message(ApplicationMessagesUtil.SUCCESS)
                        .status(HttpStatus.OK.value())
                        .build();
            }
        }

        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.INVALID_ORDER_NUMBER)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getShipmentByTrackingAndMobile(HandoverShipmentSearchDTO shipmentSearchDTO){

        ShipmentOrderDTO mobileShipmentDTO = ShipmentOrderDTO.builder()
                .id(3)
                .trackingNo(shipmentSearchDTO.getTrackingNumber())
                .mobile(shipmentSearchDTO.getMobileNumber())
                .shipmentNo("12345678")
                .orderType("COD")
                .pickupDatetime("2020-02-01-17.35.15")
                .shipperId(2)
                .deliveryCharges(15.0)
                .receiverName("Ahamd")
                .shipmentReceived(false)
                .build();

        return ApiResponse.builder()
                .data(mobileShipmentDTO)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse shipmentReceived(ReceiveShipmentSearchDTO shipmentSearch){
        ShipmentOrderDTO mobileShipmentDTO = ShipmentOrderDTO.builder()
                .id(2)
                .trackingNo(shipmentSearch.getTrackingNumber())
                .shipmentNo("123456")
                .orderType("COD")
                .pickupDatetime("21-2-2020 04:30pm")
                .shipperId(3)
                .receiverName("Ahamd")
                .deliveryCharges(5.0)
                .shipmentReceived(true)
                .build();


        return ApiResponse.builder()
                .data(mobileShipmentDTO)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse returnShipmentFromDealer(ReceiveShipmentSearchDTO shipmentSearch){
        MobileDealerReturnShipmentDTO mobileShipmentDTO = MobileDealerReturnShipmentDTO.builder()
                .trackingNo(shipmentSearch.getTrackingNumber())
                .shipmentNo("123456")
                .orderType("COD")
                .receiverName("Receiver name")
                .contact("1234567")
                .address("House Abc")
                .iqamaNumber("456798")
                .receiverPickupTime("01:30pm")
                .deliverCharges(5.0)
                .shipmentReceived(true)
                .build();

        return ApiResponse.builder()
                .data(mobileShipmentDTO)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getAgentsDriversList(String dealerId){
        CashReceiverAgentsDTO cashReceiverAgents = CashReceiverAgentsDTO.builder()
                .cashInWallet(525.5)
                .collectionAgentsList(getAgentsList())
                .driversList(getDriversList())
                .build();

        return ApiResponse.builder()
                .data(cashReceiverAgents)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse handoverCash(CashHandoverDTO cashHandoverDTO){

        return ApiResponse.builder()
                //.data(handoverShipmentDTO)
                .message(ApplicationMessagesUtil.CASH_HANDED_OVER_SUCCESSFULLY)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse handoverReceivedShipment(HandoverReceivedShipmentDTO handoverReceivedShipment){

        return ApiResponse.builder()
                //.data(handoverShipmentDTO)
                .message(ApplicationMessagesUtil.SHIPMENT_HANDED_OVER_SUCCESSFULLY)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getOrdersByStatus(OrderStatusInputDTO orderStatusInputDTO){

        if (null == orderStatusInputDTO.getOrderStatus() || null == orderStatusInputDTO.getUserId()
        || (null != orderStatusInputDTO.getOrderStatus() &&
                !(orderStatusInputDTO.getOrderStatus().equals("Active") || orderStatusInputDTO.getOrderStatus().equals("Completed")))){

            return ApiResponse.builder()
                    .message(ApplicationMessagesUtil.INVALID_ORDER_INFO)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        return ApiResponse.builder()
                .data(getShippingOrdersList(orderStatusInputDTO.getOrderStatus()))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getDealerShipments(String dealerId){

        return ApiResponse.builder()
                .data(getShippingOrdersList("Active"))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    private List<ShipmentOrderDTO> getShippingOrdersList(String status){
        List<ShipmentOrderDTO> list = new ArrayList<>();



        ShipmentOrderDTO shipmentOrder = ShipmentOrderDTO.builder()
                .createdDatetime("2020-02-01-17.35.15")
                .driverArrivalTime("2020-02-01-17.35.15")
                .id(1)
                .orderPickupTypeId(2)
                .receiverName("Ahamd")
                .receiverType("Test")
                .pickupBy("Test B")
                .shipmentClassification("Single")
                .shipmentPrice(32.5)
                .shipperId(3)
                .shipperWarehouseId(2)
                .orderType("COD")
                .status(status)
                .pickupDatetime("2020-02-01-17.35.15")
                .transitTime("17:35")
                .build();
        list.add(shipmentOrder);
        return list;
    }

    private List<DriverDTO> getDriversList(){
        List<DriverDTO> list = new ArrayList<>();
        list.add(DriverDTO.builder().id(1)
                    .firstName("Driver")
                    .lastName("A")
                    .build());
        list.add(DriverDTO.builder().id(2)
                .firstName("Driver")
                .lastName("B")
                .build());

        return list;
    }

    private List<CashCollectionAgent> getAgentsList(){
        List<CashCollectionAgent> list = new ArrayList<>();
        list.add(CashCollectionAgent.builder().id(1)
                .name("Agent 1")
                .build());
        list.add(CashCollectionAgent.builder().id(2)
                .name("Agent 2")
                .build());

        return list;
    }

    public Shipment addShipment (Shipment shipment){
        return repository.save(shipment);
    }

    public Optional<Shipment> getShipmentById(Integer id){
        return repository.findById(id);
    }

    public Iterable<Shipment> getShipmentsList() {
        return repository.findAll();
    }

    public Shipment updateShipment (Shipment shipment){
        return repository.save(shipment);
    }

    public void deleteShipment (Integer id){
        repository.deleteById(id);
    }
}
