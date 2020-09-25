package net.sarokh.api.service;

import net.sarokh.api.dao.*;
import net.sarokh.api.excel.ExcelReader;
import net.sarokh.api.model.*;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.*;
import net.sarokh.api.model.order.*;
import net.sarokh.api.model.shipper.OrderStatusReportDTO;
import net.sarokh.api.model.trip.AssignDriverToShipmentDTO;
import net.sarokh.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrderService {

    @Value("${file.upload.directory}")
    private String FILE_UPLOAD_DIR;

    @Value("${qrcode.directory}")
    private String QR_CODE_DIRECTORY;

    @Value("${barcode.directory}")
    private String BAR_CODE_DIRECTORY;

    @Value("${web.application.url}")
    private String WEB_APP_URL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ShipmentOrderItemRepository orderItemRepository;

    @Autowired
    private SarokhWarehouseRepository sarokhWarehouseRepository;

    @Autowired
    private ShipperWarehouseRepository shipperWarehouseRepository;

    @Autowired
    private DealerPointRepository dealerPointRepository;

    @Autowired
    private DeliveryChargesRepository deliveryChargesRepository;

    @Autowired
    private DriverRepository driverRepository;

    public ApiResponse createUpdateShipperOrder(List<OrderNewFormDTO> orders){
        System.out.println("Create/Update order service called. " );
        String orderId = null;
        boolean updateOrder = false;

        for (OrderNewFormDTO order: orders) {
            if (orderId == null && order.getOrderId() != null){
                orderId = order.getOrderId();
            } else {
                orderId = generateNewOrderId(order.getShipperId());
            }
            System.out.println("orderId= " + orderId);
            updateOrder = order.isUpdate();

            ShipmentOrder newOrder = getShipmentOrderObject(order, orderId);

            // If delivery location is Sarokh point then these attributes will be populated in setShipmentOrderPickupAndDelivery method
            // and set in setShippingItems methods for location.
            String locationLatitude = null;
            String locationLongitude = null;
            setShipmentOrderPickupAndDelivery(order, newOrder, locationLatitude, locationLongitude);

            if (order.getShipmentItems() != null && order.getShipmentItems().size() > 0) {
                ShipmentOrder orderCreated = repository.save(newOrder);

                List<ShipmentOrderItem> shipmentOrderItems = setShippingItems(orderCreated, order.getShipmentItems(), locationLatitude, locationLongitude);
                if (shipmentOrderItems.size() > 0) {
                    orderItemRepository.saveAll(shipmentOrderItems);
                    if (!order.isUpdate()) {
                        String msg = "You order has successfully placed.\nTracking Number: "
                                + newOrder.getOrderId() + "\nOTP: 1111";
                        SmsUtil.sendSMS(restTemplate, shipmentOrderItems.get(0).getContact(), msg);
                    }
                } else {
                    System.out.println("Shipment Order item not found");
                    return ApiResponse.builder()
                            .data(null)
                            .message(ApplicationMessagesUtil.INVALID_ORDER_INFO)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
            } else {
                return ApiResponse.builder()
                        .data(null)
                        .message(ApplicationMessagesUtil.INVALID_ORDER_INFO)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build();
            }
        }

        return ApiResponse.builder()
                .data(orderId)
                .message(updateOrder ? ApplicationMessagesUtil.ORDER_SUCCESSFULLY_UPDATED : ApplicationMessagesUtil.ORDER_SUCCESSFULLY_CREATED)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse createShipperWebOrder(List<WebOrderDTO> webOrdersList){

        List<OrderNewFormDTO> orders = new ArrayList<>();

        for (WebOrderDTO webOrder: webOrdersList) {
            List<OrderShipmentItemDTO> orderItems = new ArrayList<>();

            String additionalServices = "";

            if (webOrder.isGiftPackaging()){
                additionalServices += AdditionalServicesEnum.GiftPackaging.getServiceName() + ",";
            }
            if (webOrder.isInsurance()){
                additionalServices += AdditionalServicesEnum.Insurance.getServiceName() + ",";
            }
            if (webOrder.isNormalPackaging()){
                additionalServices += AdditionalServicesEnum.NormalPackaging.getServiceName() + ",";
            }

            orderItems.add(OrderShipmentItemDTO.builder()
                    .shipmentTitle(webOrder.getShipmentTitle())
                    .shipmentContent(webOrder.getContent())
                    .shipmentValue(webOrder.getShipmentValue())
                    .receiverName(webOrder.getReceiverName())
                    .receiverMobileNumber(webOrder.getReceiverContact())
                    .receiverAddress(webOrder.getAddress())
                    .billedAmount(webOrder.getTotal())
                    .deliveryCharges(webOrder.getShipmentCost())
                    .paymentType(webOrder.getBillingType())
                    .shipmentType(webOrder.getShipmentType())
                    .weight(webOrder.getShipmentWeight())
                    .additionalCharges(webOrder.getAdditionalCharges())
                    .codAmount(webOrder.getCodValue())
                    .locationLongitude(webOrder.getLocationLongitude())
                    .locationLatitude(webOrder.getLocationLatitude())
                    .additionalServices(additionalServices.trim().length() == 0 ? null : additionalServices)
                    .update(webOrder.isUpdate())
                    .build());

            orders.add(OrderNewFormDTO.builder()
                    .id(webOrder.getId())
                    .orderId(webOrder.getOrderId())
                    .shipFromCity(webOrder.getShipFromCity())
                    .shipToCity(webOrder.getShipToCity())
                    .pickupType(webOrder.getPickupType())
                    .deliveryLocation(webOrder.getDeliveryLocation())
                    .deliveryLocationRadio(webOrder.getDeliveryLocationRadio())
                    .shipperWarehouseId(webOrder.getShipperWarehouseId())
                    .sarokhWarehouseId(webOrder.getSarokhWarehouseId())
                    .shipperId(webOrder.getShipperId())
                    .dealerPointId(webOrder.getDealerPointId())
                    .shipmentItems(orderItems)
                    .update(webOrder.isUpdate())
                .build());
        }

        return createUpdateShipperOrder(orders);
    }

    public ApiResponse createShipperBulkOrder(OrderNewFormDTO order){
        if (ApplicationUtil.isNotNullAndEmpty(order.getOrderFile())) {
            List<OrderShipmentItemDTO> shipmentItemsList = ExcelReader.getShipmentItemsFromExcel(order, order.getOrderFile());
            List<OrderNewFormDTO> orders = new ArrayList<>();
            for (OrderShipmentItemDTO shipmentItem : shipmentItemsList){
                List<OrderShipmentItemDTO> shipmentItems = new ArrayList<>();
                shipmentItems.add(shipmentItem);

                OrderNewFormDTO newOrder = OrderNewFormDTO.builder()
                        .shipperId(order.getShipperId())
                        .shipFromCity(order.getShipFromCity())
                        .shipToCity(order.getShipToCity())
                        .pickupType(order.getPickupType())
                        .deliveryLocation(order.getDeliveryLocation())
                        .deliveryLocationRadio(order.getDeliveryLocationRadio())
                        .sarokhWarehouseId(order.getSarokhWarehouseId())
                        .shipperWarehouseId(order.getShipperWarehouseId())
                        .dealerPoint(order.getDealerPoint())
                        .dealerPointId(order.getDealerPointId())
                        .shipmentItems(shipmentItems)
                        .build();

                orders.add(newOrder);
            }
            if (shipmentItemsList.size() > 0) {
                return createUpdateShipperOrder(orders);
            }
        }

        return ApiResponse.builder()
                .message(ApplicationMessagesUtil.INVALID_ORDER_INFO)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public ApiResponse getOrderById(Integer id){
        Optional<ShipmentOrder> order = repository.findById(id);

        if (order.isPresent()){

            return ApiResponse.builder()
                    .data(order.get())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .status(HttpStatus.OK.value())
                    .build();
        }

        return ApiResponse.builder()
                .message(ApplicationMessagesUtil.INVALID_ORDER_NUMBER)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public ApiResponse editOrderDetail(Integer id){
        Optional<ShipmentOrder> order = repository.findById(id);

        if (order.isPresent()){
            ShipmentOrderItem orderItem = order.get().getShipmentOrderItems().get(0);
            String deliveryLocationRadio = null;
            if (order.get().getDeliveryLocation().equals(DeliveryTypeEnum.SarokhPoint.getDeliveryTypeName())){
                deliveryLocationRadio = "sarokhPoint";
            } else if (order.get().getDeliveryLocation().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())){
                deliveryLocationRadio = "customerAddress";
            }

            boolean normalPackaging = false;
            boolean giftPackaging = false;
            boolean insurance = false;

            if (orderItem.getAdditionalServices() != null) {
                String[] additionalServices = orderItem.getAdditionalServices().split(",");
                for (String service : additionalServices) {
                    if (service.trim().equals(AdditionalServicesEnum.NormalPackaging.getServiceName())) {
                        normalPackaging = true;
                    } else if (service.trim().equals(AdditionalServicesEnum.GiftPackaging.getServiceName())) {
                        giftPackaging = true;
                    } else if (service.trim().equals(AdditionalServicesEnum.Insurance.getServiceName())) {
                        insurance = true;
                    }
                }
            }

            return ApiResponse.builder()
                    .data(WebOrderDTO.builder()
                        .shipperId(order.get().getShipperId())
                        .shipFromCity(order.get().getShipFromCity())
                        .shipToCity(order.get().getShipToCity())
                        .pickupType(order.get().getPickupLocation())
                        .deliveryLocation(order.get().getDeliveryLocation())
                        .dealerPointId(order.get().getDeliveryLocationId())
                        .shipperWarehouseId(order.get().getPickupLocation() != null && order.get().getPickupLocation().equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName()) ? order.get().getPickupLocationId() : null )
                        .sarokhWarehouseId(order.get().getPickupLocation() != null && order.get().getPickupLocation().equals(PickupTypeEnum.SarokhWarehouse.getPickupTypeName()) ? order.get().getPickupLocationId() : null)
                        .deliveryLocationRadio(deliveryLocationRadio)
                        .shipmentTitle(orderItem.getShipmentTitle())
                        .content(orderItem.getShipmentContent())
                        .receiverName(orderItem.getReceiverName())
                        .receiverContact(orderItem.getContact())
                        .shipmentCost(orderItem.getShipmentDeliveryCharges())
                        .total(orderItem.getShipmentDeliveryCharges())
                        .shipmentWeight(orderItem.getWeight())
                        .shipmentType(orderItem.getShipmentType())
                        .billingType(orderItem.getPaymentType())
                        .codValue(orderItem.getCodAmount())
                        .shipmentValue(orderItem.getShipmentValue())
                        .insurance(insurance)
                        .normalPackaging(normalPackaging)
                        .giftPackaging(giftPackaging)
                        .address(orderItem.getAddress())
                        .locationLatitude(orderItem.getLocationLatitude())
                        .locationLongitude(orderItem.getLocationLongitude())
                        .additionalCharges(orderItem.getAdditionalCharges())
                        .build())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .status(HttpStatus.OK.value())
                    .build();
        }

        return ApiResponse.builder()
                .message(ApplicationMessagesUtil.INVALID_ORDER_NUMBER)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public ShipmentOrder getOrderByOrderId(String orderId){
        Optional<ShipmentOrder> order = repository.findByOrderId(orderId);

        if (order.isPresent()){
            return order.get();
        }

        return null;
    }

    public ApiResponse getOrdersList() {

        return ApiResponse.builder()
                .data(repository.findAllDescOrder())
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getOrderShipmentList(Integer orderId) {
        Iterable<ShipmentOrderItem> list = orderItemRepository.findAllByShipmentOrderId(orderId);
        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse searchShipperLedgerShipments(SearchShipmentDTO shipmentDTO) {

        String startDate = null;
        String endDate = null;
        Iterable<?> ordersList = null;

        if (null != shipmentDTO.getStartDate() && null != shipmentDTO.getEndDate()){
            startDate = DateUtil.convertToSqlSearchDate(DateUtil.toDateFromString(shipmentDTO.getStartDate()));
            endDate = DateUtil.convertToSqlSearchDate(DateUtil.toDateFromString(shipmentDTO.getEndDate()));

            if (shipmentDTO.getShipperId() != null && shipmentDTO.getShipperId() > 0){
                ordersList = orderItemRepository.findAllByDateAndPaymentType(
                        startDate, endDate, shipmentDTO.getShipperId());
            } else {
                ordersList = orderItemRepository.findAllByDateAndPaymentType(
                        startDate, endDate);
            }

            List<CreateBillItemsDTO> list = new ArrayList<>();

            Iterator iterator = ordersList.iterator();
            while (iterator.hasNext()){
                Object[] shipment = (Object[]) iterator.next();

                list.add(CreateBillItemsDTO.builder()
                        .id(shipment[0] != null ? Integer.parseInt(shipment[0].toString()) : 0)
                        .trackingNumber(shipment[1] != null ? shipment[1].toString() : null)
                        .date(shipment[2] != null ? shipment[2].toString() : null)
                        .amount(shipment[3] != null ? Double.parseDouble(shipment[3].toString()) : 0)
                        .build());
            }

            return ApiResponse.builder()
                    .data(list)
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .status(HttpStatus.OK.value())
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse printBulkShipments(SearchBulkShipmentDTO shipmentDTO) {

        List<ShipmentOrder> orders = repository.findOrdersNumbersInRange(shipmentDTO.getStartTrackingNumber(), shipmentDTO.getEndTrackingNumber());

        return ApiResponse.builder()
                .data(PDFPrintFileUtil.generateBulkShipmentPrintPdf(orders))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse searchShipmentByTrackingNumber(String trackingNumber) {

        Optional<ShipmentOrder> order = repository.findByOrderId(trackingNumber);

        return ApiResponse.builder()
                .data(order)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getMobileOrdersList() {

        return ApiResponse.builder()
                .data(orderItemRepository.findAll())
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getIssueShipmentsByShipperId(Integer shipperId){
        Iterable<?> allShipments = orderItemRepository.findAllShipmentsIssuesByShipperId(shipperId);
        List<ShipmentIssuesDTO> list = new ArrayList<>();

        Iterator itr = allShipments.iterator();
        while (itr.hasNext()){
            Object[] shipment = (Object[]) itr.next();
            list.add(ShipmentIssuesDTO.builder()
                    .orderId(shipment[0])
                    .shipmentId(shipment[1])
                    .reportedBy(shipment[2])
                    .issueType(shipment[3])
                    .notes(shipment[4])
                    .id(shipment[5])
                    .shipFromCity(shipment[6])
                    .shipToCity(shipment[7])
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();

    }

    public ApiResponse updateOrder (List<WebOrderDTO> orders){
        for (WebOrderDTO order: orders){

        }
        return createShipperWebOrder(orders);
    }

    public ApiResponse deleteOrder (Integer id){
        Optional<ShipmentOrder> order = repository.findById(id);
        if (order.isPresent()){

           // Set shipments = order.get().getShipmentOrderItems();
           // orderItemRepository.deleteAll(shipments);
           // order.get().setShipmentOrderItems(null);
            repository.delete(order.get());
            return ApiResponse.builder()
                    .data("Order Id=" + id + " Successfully deleted.")
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .status(HttpStatus.OK.value())
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.UNABLE_TO_DELETE)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public ApiResponse getOrdersByType(OrderTypeInputDTO typeInputDTO){

        return ApiResponse.builder()
                //.data(repository.findShipmentOrderByOrderType(type))
                .data(getShippingOrdersList(typeInputDTO.getOrderType(), "Active"))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    private List<ShipmentOrderDTO> getShippingOrdersList(String type, String status){
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
                .orderType(type)
                .status(status)
                .pickupDatetime("2020-02-01-17.35.15")
                .transitTime("17:35")
                .build();
        list.add(shipmentOrder);
        return list;
    }

    public ApiResponse createShipperNewOrderId(Integer shipperId){

        return ApiResponse.builder()
                .data(generateNewOrderId(shipperId))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public String generateNewOrderId(Integer shipperId){
        Object maxOrderId = repository.getMaxOrderIdByShipperId(shipperId);
        // Order Id Format = Shipper(ShipperId)-(totalOrders+1)
        String shipperStrId = shipperId.toString();

        String newOrderShipperId = "";
        for (int i=0; i<5-shipperStrId.length(); i++){
            newOrderShipperId += "0";
        }

        newOrderShipperId += shipperId;

        String newOrderId = "";

        if (null != maxOrderId){
            String currentMaxId = maxOrderId.toString();
            Integer intValue = Integer.parseInt(currentMaxId) + 1;
            String newId = "" + intValue;
            while (newId.length() < currentMaxId.length()){
                newId = "0" + newId;
            }
            newOrderId = newId;
        } else {
            newOrderId = newOrderShipperId + "000001";
        }

        return newOrderId;
    }

    public ApiResponse getAllShipmentsByShipperId(Integer shipperId){
        Iterable<ShipmentOrder> allShipments = repository.findAllByShipperId(shipperId);
        List<AllShipmentDTO>  list = new ArrayList<>();

        Iterator<ShipmentOrder> itr = allShipments.iterator();
        while (itr.hasNext()){
            ShipmentOrder shipment =  itr.next();
            DateTimeDTO dateTime = DateUtil.convertToDateAndTime(shipment.getCreatedDatetime());
            System.out.println("All shipment date: " + dateTime.getDate());

            String receiverName = null;
            String deliveryStatus = null;
            if (shipment.getShipmentOrderItems() != null && shipment.getShipmentOrderItems().size() > 0){
                receiverName = shipment.getShipmentOrderItems().get(0).getReceiverName();
                deliveryStatus = shipment.getShipmentOrderItems().get(0).getDeliveryStatus();

            }

            list.add(AllShipmentDTO.builder()
                    .id(shipment.getId())
                    .orderId(shipment.getOrderId())
                    .dateTime(dateTime.getDate() + " " + dateTime.getTime())
                    .pickType(shipment.getPickupLocation())
                    .deliveryType(shipment.getDeliveryLocation())
                    .receiverName(receiverName)
                    .status(deliveryStatus)
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getAllShipmentsTrackingNumberByShipperId(Integer shipperId){
        Iterable<?> allShipments = repository.findAllShipmentsByShipperId(shipperId);
        List<String>  list = new ArrayList<>();

        Iterator itr = allShipments.iterator();
        while (itr.hasNext()){
            Object[] shipment = (Object[]) itr.next();
            list.add(shipment[0].toString());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getAllShipmentsTrackingNumber(){
        Iterable<ShipmentOrder> allShipments = repository.findAll();
        List<String>  list = new ArrayList<>();

        Iterator<ShipmentOrder> itr = allShipments.iterator();
        while (itr.hasNext()){
            ShipmentOrder shipmentOrder =  itr.next();
            list.add(shipmentOrder.getOrderId());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getCODShipmentsByShipperId(Integer shipperId){
        Iterable<?> allShipments = repository.findCODShipmentsByShipperId(shipperId);
        List<CODShipmentsDTO>  list = new ArrayList<>();

        Iterator itr = allShipments.iterator();
        while (itr.hasNext()){
            Object[] shipment = (Object[]) itr.next();
            DateTimeDTO dateTimeDTO = DateUtil.convertToDateAndTime(DateUtil.toDateFromSQLString(shipment[1].toString()));
            list.add(CODShipmentsDTO.builder()
                    .orderId(shipment[0])
                    .dateTime(dateTimeDTO != null ? dateTimeDTO.getDate() : null)
                    .receiverName(shipment[2])
                    .codAmount(shipment[3])
                    .status(shipment[4])
                    .id(shipment[5])
                    .shipFromCity(shipment[6])
                    .shipToCity(shipment[7])
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getReturnedShipmentsByShipperId(Integer shipperId){
        List<ShipmentOrderItem> orderList = orderItemRepository.findAllByDeliveryStatusAndShipperId(OrderDeliveryStatusEnum.Returned.name(), shipperId);

        return ApiResponse.builder()
                .data(orderList)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getPendingOrderByShipperId(Integer shipperId){
        Iterable<?> orderList = repository.findAllPendingShipmentsByShipperId(shipperId);
        List<PendingShipmentsDTO>  list = new ArrayList<>();

        Iterator itr = orderList.iterator();
        while (itr.hasNext()){
            Object[] order = (Object[]) itr.next();
            list.add(PendingShipmentsDTO.builder()
                    .orderId(order[0])
                    .dateTime(order[1])
                    .status(order[2])
                    .receiverName(order[3])
                    .paymentType(order[4])
                    .codAmount(order[5])
                    .id(order[6])
                    .shipFromCity(order[7])
                    .shipToCity(order[8])
                    .shipmentTitle(order[9])
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse findAllByDeliveryStatusAndPaymentType(Integer shipperId){
        Iterable<?> orderList = orderItemRepository.findAllByDeliveryStatusAndPaymentType(shipperId);

        Map<String, OrderStatusReportDTO> ordersReport = new HashMap<>();
        Iterator itr = orderList.iterator();

        while (itr.hasNext()) {
            Object[] status = (Object[]) itr.next();
            String key = status[1].toString();

            if(null != status[1] && (status[1].toString().equals(OrderDeliveryStatusEnum.Pending.toString())
                    || status[1].toString().equals(OrderDeliveryStatusEnum.Active.toString())
                    || status[1].toString().equals(OrderDeliveryStatusEnum.NoResponse.toString()))){
                key = OrderDeliveryStatusEnum.InProcess.toString();
            }

            OrderStatusReportDTO dto = new OrderStatusReportDTO();

            if ( null != ordersReport.get(key)) {
                dto = ordersReport.get(key);
            }

            if(status[0].toString().equals(PaymentTypeEnum.COD.toString())){
                dto.setCodOrders(dto.getCodOrders() + Integer.parseInt(status[2].toString()));
            } else if(status[0].toString().equals(PaymentTypeEnum.Prepaid.toString())){
                dto.setPrepaidOrders(dto.getPrepaidOrders() + Integer.parseInt(status[2].toString()));
            } else if(status[0].toString().equals(PaymentTypeEnum.FullPrepaid.toString())){
                dto.setFullPrepaidOrders(dto.getFullPrepaidOrders() + Integer.parseInt(status[2].toString()));
            }

            ordersReport.put(key, dto);
        }

        return ApiResponse.builder()
                .data(ordersReport)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse findShipmentsByDeliveryStatus(String status){
        Iterable<?> shipmentList = orderItemRepository.findAllShipmentsByDeliveryStatus(status);
        return parseShipmentsListingResponse(shipmentList);
    }

    public ApiResponse findAllShipments(){
        Iterable<?> shipmentList = orderItemRepository.findAllShipments();
        return parseAllShipmentsListingResponse(shipmentList);
    }

    public ApiResponse findShipmentsWithIssues(){
        Iterable<?> shipmentList = orderItemRepository.findAllShipmentsWithIssues();
        return parseShipmentsListingResponse(shipmentList);
    }

    public ApiResponse findShipmentsByPaymentType(String paymentType){
        Iterable<?> shipmentList = orderItemRepository.findAllShipmentsByPaymentType(paymentType);
        return parseCodShipmentsListingResponse(shipmentList);
    }

    public ApiResponse findShipmentsByDeliveryOrPickup(String pickupDelivery){
        Iterable<?> shipmentList = orderItemRepository.findShipmentsByDeliveryOrPickup(pickupDelivery);
        return parsePickupDeliveryShipmentsResponse(shipmentList);
    }

    public ApiResponse findDealerInventory(){
        Iterable<?> shipmentList = orderItemRepository.findDealerInventory();
        return parsePickupDeliveryShipmentsResponse(shipmentList);
    }

    public boolean assignCardToShipment(AssignCardToShipmentDTO dto){
        Optional<ShipmentOrder> order = repository.findByOrderId(dto.getTrackingNumber());
        if (order.isPresent()){
            order.get().setAssignTo(ShipmentAssigneeEnum.Card.name());
            order.get().setAssignToId(dto.getWarehouseId());
            order.get().setStatus(OrderDeliveryStatusEnum.InProcess.name());
            order.get().setDispatchTripId(null);

            if (order.get().getShipmentOrderItems() != null && order.get().getShipmentOrderItems().size() > 0){
                order.get().getShipmentOrderItems().get(0).setDeliveryStatus(OrderDeliveryStatusEnum.InProcess.name());

                Optional<SarokhWarehouse> sarokhWarehouse = sarokhWarehouseRepository.findById(dto.getWarehouseId());
                if (sarokhWarehouse.isPresent()) {
                    order.get().setAssignToDetail(sarokhWarehouse.get().getName());
                    order.get().setTransitLocationLatitude(sarokhWarehouse.get().getLocationLatitude());
                    order.get().setTransitLocationLongitude(sarokhWarehouse.get().getLocationLongitude());
                }
            } else {
                System.out.println("No shipment items in order. orderId=" + order.get().getOrderId());
            }

            repository.save(order.get());
            return true;
        }

        return false;
    }

    public boolean assignDriverToShipment(AssignDriverToShipmentDTO dto){
        Optional<ShipmentOrder> order = repository.findByOrderId(dto.getTrackingNumber());
        if (order.isPresent()){
            Optional<Driver> driver = driverRepository.findById(dto.getDriverId());
            order.get().setAssignTo(ShipmentAssigneeEnum.Driver.name());
            order.get().setAssignToId(dto.getDriverId());
            if (driver.isPresent()) {
                order.get().setAssignToDetail(driver.get().getFirstName() + " " + driver.get().getLastName());
            }
            repository.save(order.get());
            return true;
        }

        return false;
    }

    public List<ShipmentOrder> findShipmentsByAssignedToWarehouse(Integer warehouseId){
        List<ShipmentOrder> shipmentList = repository.findAllByAssignToAndAssignToId(ShipmentAssigneeEnum.Card.name(), warehouseId);
        return shipmentList;
    }

    public ApiResponse getOrdersLocation() {
        Iterable<?> orderLocations = repository.findAllOrdersLocations();
        List<MapLocationDTO> mapLocations = new ArrayList<>();

        Iterator itr = orderLocations.iterator();
        while (itr.hasNext()){
            Object[] order = (Object[]) itr.next();
            mapLocations.add(MapLocationDTO.builder()
                    .label(order[0] != null ? order[0].toString() : null)
                    .longitude(order[1] != null ? order[1].toString() : null)
                    .latitude(order[2] != null ? order[2].toString() : null)
                    .build());
        }

        return ApiResponse.builder()
                .data(mapLocations)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ShipmentPickupDeliveryDTO getPickupDeliveryLocations(Integer shipperId){
        List<SarokhWarehouse> sarokhWarehouses = sarokhWarehouseRepository.findAll();
        Iterable<ShipperWarehouse> shipperWarehouses = shipperWarehouseRepository.findAllByShipperId(shipperId);
        List<DealerPoint> dealerPoints = dealerPointRepository.findAll();

        return ShipmentPickupDeliveryDTO.builder()
                .sarokhWarehouses(sarokhWarehouses)
                .sarokhPoints(dealerPoints)
                .shipperWarehouses(shipperWarehouses)
                .build();
    }

    private List<ShipmentOrderItem> setShippingItems(ShipmentOrder orderCreated, List<OrderShipmentItemDTO> itemsList, String locationLatitude, String locationLongitude){
        System.out.println("List size= " + itemsList.size());
        List<ShipmentOrderItem> items = new ArrayList<>();

        itemsList.forEach(orderShipmentItemDTO -> {
            ShipmentOrderItem orderItem = new ShipmentOrderItem();
            orderItem.setTrackingNumber(orderCreated.getOrderId());
            orderItem.setShipmentOrderId(orderCreated.getId());
            orderItem.setShipmentTitle(orderShipmentItemDTO.getShipmentTitle());
            orderItem.setShipmentContent(orderShipmentItemDTO.getShipmentContent());
            orderItem.setShipmentValue(orderShipmentItemDTO.getShipmentValue());
            orderItem.setPaymentType(orderShipmentItemDTO.getPaymentType());
            orderItem.setCodAmount(orderShipmentItemDTO.getCodAmount());
            orderItem.setShipmentType(orderShipmentItemDTO.getShipmentType());
            orderItem.setShipmentDeliveryCharges(orderShipmentItemDTO.getDeliveryCharges());
            orderItem.setWeight(orderShipmentItemDTO.getWeight());
            orderItem.setLocationLatitude(locationLatitude == null ? orderShipmentItemDTO.getLocationLatitude() : locationLatitude);
            orderItem.setLocationLongitude(locationLongitude == null ? orderShipmentItemDTO.getLocationLongitude() : locationLongitude);
            orderItem.setAdditionalCharges(orderShipmentItemDTO.getAdditionalCharges());
            orderItem.setReceiverName(orderShipmentItemDTO.getReceiverName());
            orderItem.setAddress(orderShipmentItemDTO.getReceiverAddress());
            orderItem.setContact(orderShipmentItemDTO.getReceiverMobileNumber());

            orderItem.setAdditionalServices(orderShipmentItemDTO.getAdditionalServices() != null ? orderShipmentItemDTO.getAdditionalServices().toString() : null);

            if (!orderShipmentItemDTO.isUpdate()){
                orderItem.setReceiverConfirmation(ReceiverConfirmationEnum.No.name());
                orderItem.setDeliveryStatus(OrderDeliveryStatusEnum.Pending.name());
                orderItem.setReceivedFromShipper(ShipmentReceivedStatusEnum.No.name());

                String qrcodeFile = FILE_UPLOAD_DIR + QR_CODE_DIRECTORY + orderItem.getTrackingNumber() + ".png";
                String generatedFile = QRCodeGenerator.generateQRCode(orderItem.getTrackingNumber(), qrcodeFile);
                orderItem.setQRCode(WEB_APP_URL + QR_CODE_DIRECTORY + generatedFile);

                String barcodeFile = FILE_UPLOAD_DIR + BAR_CODE_DIRECTORY + orderItem.getTrackingNumber() + ".png";
                String barCodeGeneratedFile = BarCodeGenerator.generateBarCode(orderItem.getTrackingNumber(), barcodeFile);
                if (null != barCodeGeneratedFile) {
                    orderItem.setBarCode(barCodeGeneratedFile.replaceAll(FILE_UPLOAD_DIR, WEB_APP_URL));
                }
            } else {
                if (orderCreated.getShipmentOrderItems().size() > 0 ) {
                    orderItem.setId(orderCreated.getShipmentOrderItems().get(0).getId());
                    orderItem.setReceiverConfirmation(orderCreated.getShipmentOrderItems().get(0).getReceiverConfirmation());
                    orderItem.setDeliveryStatus(orderCreated.getShipmentOrderItems().get(0).getDeliveryStatus());
                    orderItem.setReceivedFromShipper(orderCreated.getShipmentOrderItems().get(0).getReceivedFromShipper());
                }
            }

            items.add(orderItem);
        });

        return items;
    }

    private Double getDeliveryCharges (Integer shipperId, String deliveryType, String weight, String paymentType){
        Double deliveryAmount = 0.0;
        Optional<DeliveryCharges> deliveryCharges = null;

        if (deliveryType.equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())){
            //deliveryCharges = deliveryChargesRepository.findByChargesTypeAndShipperId("Last Mile", shipperId);
        } else {
           // deliveryCharges = deliveryChargesRepository.findByChargesTypeAndShipperId("PUDO", shipperId);
        }

        if (deliveryCharges != null &&  deliveryCharges.isPresent()) {
            if (paymentType.equals("COD")){
                deliveryAmount = deliveryCharges.get().getCODCharges();
            } else {
                if (weight.equals("Upto 5 kg")) {
                    deliveryAmount = deliveryCharges.get().getWeightUptoFiveKg();
                } else if (weight.equals("5 kg to 10 kg")) {
                    deliveryAmount = deliveryCharges.get().getWeightFiveToTen();
                } else {
                    // "10 to 15 kg or above"
                    deliveryAmount = deliveryCharges.get().getWeightTenToFifteen();
                }
            }
        }

        return deliveryAmount;
    }

    private ShipmentOrder getShipmentOrderObject(OrderNewFormDTO order, String orderId){
        ShipmentOrder newOrder = null;

        if (order.isUpdate()){
            Optional<ShipmentOrder> existingOrder = repository.findById(order.getId());
            newOrder = existingOrder.isPresent() ? existingOrder.get() : new ShipmentOrder();
        } else {
            newOrder = new ShipmentOrder();
        }

        newOrder.setOrderId(orderId);
        newOrder.setShipFromCity(order.getShipFromCity());
        newOrder.setShipToCity(order.getShipToCity());
        newOrder.setShipperId(order.getShipperId());

        if (!order.isUpdate()) {
            newOrder.setStatus(OrderDeliveryStatusEnum.Pending.name());
            newOrder.setAssignTo(ShipmentAssigneeEnum.Shipper.name());
        }

        newOrder.setAssignToId(order.getShipperId());
        newOrder.setReceiverType(ShipmentTypeEnum.SingleReceiver.name()); // Currently dealing only single receiver/single shipment

        return newOrder;
    }

    private void setShipmentOrderPickupAndDelivery(OrderNewFormDTO order, ShipmentOrder newOrder,String locationLatitude,String locationLongitude){

        // Set Pickup Location Details

        newOrder.setPickupLocation(order.getPickupType());

        if (order.getPickupType().equals(PickupTypeEnum.SarokhWarehouse.getPickupTypeName())
                && null != order.getSarokhWarehouseId()) {
            newOrder.setPickupLocationId(order.getSarokhWarehouseId());
            Optional<SarokhWarehouse> sarokhWarehouse = sarokhWarehouseRepository.findById(order.getSarokhWarehouseId());
            if (sarokhWarehouse.isPresent()) {
                newOrder.setPickupLocationDetail(sarokhWarehouse.get().getName());
                newOrder.setTransitLocationLatitude(sarokhWarehouse.get().getLocationLatitude());
                newOrder.setTransitLocationLongitude(sarokhWarehouse.get().getLocationLongitude());
            }
        } else if (order.getPickupType().equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName())
                && null != order.getShipperWarehouseId()) {
            newOrder.setPickupLocationId(order.getShipperWarehouseId());
            Optional<ShipperWarehouse> shipperWarehouse = shipperWarehouseRepository.findById(order.getShipperWarehouseId());
            if (shipperWarehouse.isPresent()) {
                newOrder.setPickupLocationDetail(shipperWarehouse.get().getName());
                newOrder.setTransitLocationLatitude(shipperWarehouse.get().getLocationLatitude());
                newOrder.setTransitLocationLongitude(shipperWarehouse.get().getLocationLongitude());
            }
        }

        // Set Delivery Location Details

        newOrder.setDeliveryLocation(order.getDeliveryLocation());

        if (order.getDeliveryLocation().equals(DeliveryTypeEnum.PredefinedLocation.getDeliveryTypeName())){
            newOrder.setDeliveryLocationDetail(order.getShipmentItems().get(0).getReceiverAddress());
        } else if (order.getDeliveryLocation().equals(DeliveryTypeEnum.SarokhPoint.getDeliveryTypeName())){
            if (order.getDeliveryLocationRadio() != null && order.getDeliveryLocationRadio().equals("customerAddress")){
                newOrder.setDeliveryLocation(DeliveryTypeEnum.LastMile.getDeliveryTypeName());
            } else {
                newOrder.setDeliveryLocationId(order.getDealerPointId());
                if (order.getDealerPointId() != null) {
                    Optional<DealerPoint> dealerPoint = dealerPointRepository.findById(order.getDealerPointId());
                    if (dealerPoint.isPresent()) {
                        newOrder.setDeliveryLocationDetail(dealerPoint.get().getDealerPointName());
                        // Set values of lat, long and will be utilized in shipmentOrderItem
                        locationLatitude = dealerPoint.get().getLocationLatitude();
                        locationLongitude = dealerPoint.get().getLocationLongitude();
                    } else {
                        newOrder.setDeliveryLocationDetail(order.getDeliveryLocation());
                    }
                } else {
                    newOrder.setDeliveryLocationDetail(order.getDeliveryLocation());
                }
            }
        }
    }

    private ApiResponse parseAllShipmentsListingResponse(Iterable<?> shipmentList){
        List<ShipmentListingDTO>  list = new ArrayList<>();

        Iterator itr = shipmentList.iterator();
        while (itr.hasNext()){
            Object[] order = (Object[]) itr.next();
            DateTimeDTO dateTimeDTO = DateUtil.convertToDateAndTime(DateUtil.toDateFromSQLString(order[1].toString()));

            list.add(ShipmentListingDTO.builder()
                    .shipmentId(order[0])
                    .dateTime(dateTimeDTO != null ? dateTimeDTO.getDate() : null)
                    .fromCity(order[2])
                    .toCity(order[3])
                    .status(order[4])
                    .shipper(order[5] + " " +  order[6])
                    .id(order[10])
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    private ApiResponse parseCodShipmentsListingResponse(Iterable<?> shipmentList){
        List<ShipmentListingDTO>  list = new ArrayList<>();

        Iterator itr = shipmentList.iterator();
        while (itr.hasNext()){
            Object[] order = (Object[]) itr.next();
            DateTimeDTO dateTimeDTO = DateUtil.convertToDateAndTime(DateUtil.toDateFromSQLString(order[1].toString()));

            list.add(ShipmentListingDTO.builder()
                    .shipmentId(order[0])
                    .dateTime(dateTimeDTO != null ? dateTimeDTO.getDate() : null)
                    .fromCity(order[2])
                    .toCity(order[3])
                    .status(order[4])
                    .shipper(order[5] + " " +  order[6])
                    .codAmount(order[7])
                    .id(order[8])
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    private ApiResponse parseShipmentsListingResponse(Iterable<?> shipmentList){
        List<ShipmentListingDTO>  list = new ArrayList<>();

        Iterator itr = shipmentList.iterator();
        while (itr.hasNext()){
            Object[] order = (Object[]) itr.next();
            DateTimeDTO dateTimeDTO = DateUtil.convertToDateAndTime(DateUtil.toDateFromSQLString(order[1].toString()));
            String currentLocation = null;

            if (order[11] != null){
                currentLocation = order[11].toString();
                if (currentLocation.equals(ShipmentAssigneeEnum.Card.name())){
                    currentLocation = PickupTypeEnum.SarokhWarehouse.getPickupTypeName();
                }
            }

            list.add(ShipmentListingDTO.builder()
                    .shipmentId(order[0])
                    .dateTime(dateTimeDTO != null ? dateTimeDTO.getDate() : null)
                    .fromCity(order[14])
                    .toCity(order[15])
                    .status(order[6])
                    .shipper(order[4] + " " +  order[5])
                    .currentLocation(currentLocation)
                    .deliveryType(order[16])
                    .id(order[10])
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    private ApiResponse parsePickupDeliveryShipmentsResponse(Iterable<?> shipmentList){
        List<ShipmentListingDTO>  list = new ArrayList<>();

        Iterator itr = shipmentList.iterator();
        while (itr.hasNext()){
            Object[] order = (Object[]) itr.next();
            DateTimeDTO dateTimeDTO = DateUtil.convertToDateAndTime(DateUtil.toDateFromSQLString(order[1].toString()));
            list.add(ShipmentListingDTO.builder()
                    .shipmentId(order[0])
                    .dateTime(dateTimeDTO != null ? dateTimeDTO.getDate() : null)
                    //.deliveryLocation(order[2])
                    .status(order[3])
                    .assignToDriver(order[4] + " " +  order[5])
                    .id(order[6])
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }
}
