package net.sarokh.api.service.mobile;

import net.sarokh.api.dao.*;
import net.sarokh.api.model.ShipmentOrderDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.*;
import net.sarokh.api.model.mobile.*;
import net.sarokh.api.model.order.ReceiverConfirmationEnum;
import net.sarokh.api.service.OrderService;
import net.sarokh.api.service.UserService;
import net.sarokh.api.service.WalletService;
import net.sarokh.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MobileDealerService {

    @Value("${file.upload.directory}")
    private String FILE_UPLOAD_DIR;

    @Value("${qrcode.directory}")
    private String QR_CODE_DIRECTORY;

    @Value("${barcode.directory}")
    private String BAR_CODE_DIRECTORY;

    @Value("${web.application.url}")
    private String WEB_APP_URL;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShipmentOrderItemRepository shipmentOrderItemRepository;

    @Autowired
    private DealerPointRepository dealerPointRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private DispatchTripRepository tripRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private ShipmentIssueReportRepository issueReportRepository;

    @Autowired
    private SarokhTaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SarokhTaskConfirmationRepository taskConfirmationRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderService orderService;

    // Mocked with Static data. Will be enabled after testing
    public MobileDealerDashboardDTO getDealerDashboard(Integer dealerId){

        DealerPoint point = getDealerPointByDealerId(dealerId);

        if (point != null){
            orderRepository.findAllByAssignToAndAssignToId(PickupTypeEnum.DealerPoint.getPickupTypeName(), point.getId());

        }

        MobileDealerDashboardDTO dashboardDTO = MobileDealerDashboardDTO.builder()
                .availableShipments(12)
                .walletBalance(1012)
                .shipmentsIn(7)
                .shipmentsOut(3)
                .cashIn(3659.00)
                .cashOut(256.0)
                .build();


        return dashboardDTO;
    }

    // Done
    public ApiResponse getSarokhTask(Integer dealerId){

        SarokhTaskRequestDTO sarokhTask = getSarokhTaskForDealer(dealerId);

        if (sarokhTask == null){

            return ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.OK.value())
                    .message("No Task for Dealer")
                    .build();
        }

        return ApiResponse.builder()
                .data(sarokhTask)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    // Done
    private SarokhTaskRequestDTO getSarokhTaskForDealer(Integer dealerId){

        DealerPoint point = getDealerPointByDealerId(dealerId);

        Iterable<SarokhTask> sarokhTask = taskRepository.getDealerPointSarokhTasks(point.getId());
        Iterator<SarokhTask> iterator = sarokhTask.iterator();

        while(iterator.hasNext()) {

            SarokhTask task = iterator.next();
            String[] giveShipmentsArray = task.getGiveShipments() != null ? task.getGiveShipments().split(",") : null;
            String[] receiveShipmentsArray = task.getReceiveShipments() != null ? task.getReceiveShipments().split(",") : null;

            return SarokhTaskRequestDTO.builder()
                    .giveShipments(giveShipmentsArray != null ? giveShipmentsArray.length : 0)
                    .receiveShipments(receiveShipmentsArray != null ? receiveShipmentsArray.length : 0 )
                    .payCOD(task.getCodAmount())
                    .driverId(task.getDriverId()+"")
                    .driverName(task.getDriverName())
                    .taskId(task.getId())
                    .allShipmentsGiven(false)
                    .allShipmentsGiven(true)
                    .isCODPaid(false)
                    .tripId(task.getTripId())
                    .build();
        }

        return null;
    }

    // Done
    public ApiResponse getReceiveShipmentsDetail(Integer dealerId){
        List<ReceiveShipmentsDetailDTO> list = new ArrayList<>();
        DealerPoint point = getDealerPointByDealerId(dealerId);

        if (point != null) {
            Iterable<SarokhTask> sarokhTask = taskRepository.getDealerPointSarokhTasks(point.getId());
            Iterator<SarokhTask> iterator = sarokhTask.iterator();

            while (iterator.hasNext()) {
                SarokhTask task = iterator.next();
                // Driver Give shipments will be Dealer Receive shipments
                String[] receiveShipmentsArray = task.getGiveShipments() != null ? task.getGiveShipments().split(",") : null;

                if (receiveShipmentsArray != null) {

                    for (int i = 0; i < receiveShipmentsArray.length; i++) {

                        Optional<ShipmentOrder> shipment = orderRepository.findByOrderId(receiveShipmentsArray[i]);
                        if (shipment.isPresent()) {
                            String receivedStatus = "No";
                            if (shipment.isPresent() && shipment.get().getAssignTo().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())) {
                                receivedStatus = "Yes";
                            }

                            list.add(ReceiveShipmentsDetailDTO.builder()
                                    .trackingNo(shipment.get().getShipmentOrderItems().get(0).getTrackingNumber())
                                    .receiverName(shipment.get().getShipmentOrderItems().get(0).getReceiverName())
                                    .billedAmount(shipment.get().getShipmentOrderItems().get(0).getShipmentBilledAmount())
                                    .receivedStatus(receivedStatus)
                                    .build());
                        }
                    }
                }

            }
        }

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    // Done
    public ApiResponse receiveShipment(DealerReceiveShipmentDTO receiveShipmentDTO){

        Optional<ShipmentOrder> shipmentOrder = orderRepository.findByOrderId(receiveShipmentDTO.getTrackingNumber());

        if (shipmentOrder.isPresent()){
            DealerPoint dealerPoint = getDealerPointByDealerId(receiveShipmentDTO.getDealerId());
            shipmentOrder.get().setAssignTo(PickupTypeEnum.DealerPoint.getPickupTypeName());
            shipmentOrder.get().setAssignToId(dealerPoint.getId());
            shipmentOrder.get().setAssignToDetail(dealerPoint.getDealerPointName());
            shipmentOrder.get().setPickupLocationDetail(dealerPoint.getDealerPointName());
            shipmentOrder.get().setPickupLocationId(dealerPoint.getId());
            orderRepository.save(shipmentOrder.get());
        } else {
            return ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(ApplicationMessagesUtil.INVALID_TRACKING_NUMBER)
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    // Done
    public ApiResponse getGiveShipmentsDetail(Integer dealerId){
        List<ReceiveShipmentsDetailDTO> list = new ArrayList<>();
        DealerPoint point = getDealerPointByDealerId(dealerId);

        if (point != null) {

            Iterable<SarokhTask> sarokhTask = taskRepository.getDealerPointSarokhTasks(point.getId());
            Iterator<SarokhTask> iterator = sarokhTask.iterator();

            while(iterator.hasNext()) {
                SarokhTask task = iterator.next();
                // Driver Receive shipments will be Dealer Give shipments
                String[] giveShipmentsArray  = task.getReceiveShipments() != null ? task.getReceiveShipments().split(",") : null;

                if (giveShipmentsArray != null){

                    for (int i=0; i< giveShipmentsArray.length; i++){

                        Optional<ShipmentOrder> shipment = orderRepository.findByOrderId(giveShipmentsArray[i]);
                        String giveStatus = "No";
                        if (shipment.isPresent() && shipment.get().getAssignTo().equals(ShipmentAssigneeEnum.Driver.name())) {
                            giveStatus = "Yes";
                        }
                        if (shipment.isPresent()){
                            list.add(ReceiveShipmentsDetailDTO.builder()
                                    .trackingNo(shipment.get().getOrderId())
                                    .type("Shipper")
                                    .giveStatus(giveStatus)
                                    .build());
                        }
                    }
                }

            }
        }

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();

    }

    // Done
    public ApiResponse getPayCODDetail(Integer dealerId){

        List<ReceiveShipmentsDetailDTO> list = new ArrayList<>();
        DealerPoint point = getDealerPointByDealerId(dealerId);

        if (point != null) {

            Iterable<SarokhTask> sarokhTask = taskRepository.getDealerPointSarokhTasks(point.getId());
            Iterator<SarokhTask> iterator = sarokhTask.iterator();

            while(iterator.hasNext()) {
                SarokhTask task = iterator.next();
                // Driver Receive shipments will be Dealer Give shipments
                String[] giveShipmentsArray  = task.getReceiveShipments() != null ? task.getReceiveShipments().split(",") : null;

                if (giveShipmentsArray != null){

                    for (int i=0; i< giveShipmentsArray.length; i++){

                        Optional<ShipmentOrderItem> shipment = shipmentOrderItemRepository.findByTrackingNumber(giveShipmentsArray[i]);
                        if (shipment.isPresent()){
                            list.add(ReceiveShipmentsDetailDTO.builder()
                                    .trackingNo(shipment.get().getTrackingNumber())
                                    .deliveryDate(shipment.get().getDeliveryDate())
                                    .billedAmount(shipment.get().getShipmentBilledAmount())
                                    .build());
                        }
                    }
                    list.add(ReceiveShipmentsDetailDTO.builder()
                            .billNo("0514")
                            .deliveryDate("21-7-2020")
                            .billedAmount(456.0)
                            .build());
                }

            }
        }

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getCODLedger(Integer userId){

        Iterable<PaymentTransaction> transactions = paymentTransactionRepository.findAllByPaymentMethodAndUserId(PaymentTypeEnum.COD.name(), userId);
        Iterator<PaymentTransaction> itr = transactions.iterator();

        List<MobileCODLedgerDTO> list = new ArrayList<>();

        while (itr.hasNext()) {
            PaymentTransaction transaction = itr.next();
            list.add(MobileCODLedgerDTO.builder()
                    .ledgerId(transaction.getTransactionId())
                    .deliveryDate(transaction.getCreatedDatetime())
                    .amountPaid(transaction.getAmountPaid())
                    .pendingAmount(transaction.getAmountPending())
                    .driverId(transaction.getTransactionToId())
                    .driverName(transaction.getTransactionTo())
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse getShippersListWithPendingOrders(){

        Iterable<?> shippers = shipmentOrderItemRepository.findAllShippersWithPendingOrders();

        List<MobileShippersListDTO> list = new ArrayList<>();

        Iterator itr = shippers.iterator();
        while (itr.hasNext()){
            Object[] shipment = (Object[]) itr.next();
            list.add(MobileShippersListDTO.builder()
                    .shipperId((Integer) shipment[0])
                    .shipperName(shipment[1].toString() + " " + shipment[2].toString())
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse getShipperReceiveList(Integer shipperId){

        Iterable<?> shipmentIdsList = orderRepository.getDealerShipperReceiveList(shipperId);

        List<String> trackingNumbersList = new ArrayList<>();
        Iterator itr = shipmentIdsList.iterator();

        while (itr.hasNext()) {
            Object[] shipmentId = (Object[]) itr.next();
                    trackingNumbersList.add(shipmentId[0].toString());
        }

        Optional<Shipper> shipper = shipperRepository.findById(shipperId);

        return ApiResponse.builder()
                .data(MobileShippersListDTO.builder()
                        .shipperId(shipperId)
                        .shipperName(shipper.get().getFirstName() + " " + shipper.get().getLastName())
                        .shipmentList(trackingNumbersList)
                        .build())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    // Done
    public ApiResponse submitSarokhTask(SarokhTaskSubmitDTO sarokhTaskDTO){

        DealerPoint point = getDealerPointByDealerId(sarokhTaskDTO.getDealerId());

        SarokhTaskRequestDTO task = getSarokhTaskForDealer(sarokhTaskDTO.getDealerId());

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setAmountPaid(task.getPayCOD());
        transaction.setDetails("Paid COD to driver");
        transaction.setTransactionFrom(PickupTypeEnum.DealerPoint.getPickupTypeName());
        transaction.setTransactionFromId(point.getId());
        transaction.setTransactionToId(Integer.parseInt(task.getDriverId()));
        transaction.setTransactionTo(task.getDriverName());

        Optional<Dealer> dealer = dealerRepository.findById(sarokhTaskDTO.getDealerId());

        if (dealer.isPresent()){
            transaction.setUserId(dealer.get().getUserId());
        }

        paymentTransactionRepository.save(transaction);

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SAROKH_TASK_SUBMITTED)
                .build();
    }

    // Done
    public ApiResponse requestSarokhTaskConfirmation(Integer dealerId){

        SarokhTaskRequestDTO task = getSarokhTaskForDealer(dealerId);

        Integer driverId = task.getDriverId() != null ? Integer.parseInt(task.getDriverId()) : 0;

        if (driverId != 0 && task != null) {
            List<SarokhTaskConfirmation> taskConfirmations = taskConfirmationRepository.checkSarokhTaskConfirmationForDriver(driverId);
            String taskId = "-1";

            if (taskConfirmations != null && taskConfirmations.size() == 0) {

                SarokhTaskConfirmation taskConfirmation = new SarokhTaskConfirmation();
                taskConfirmation.setStatus(DispatchTripStatusEnum.Active.name());
                taskConfirmation.setReceiveCod(false);
                taskConfirmation.setPendingCod(false);
                taskConfirmation.setGiveShipments(false);
                taskConfirmation.setReceiveShipments(false);
                taskConfirmation.setSarokhTaskId(task.getTaskId());
                taskConfirmation.setDriverId(Integer.parseInt(task.getDriverId()));
                taskConfirmationRepository.save(taskConfirmation);
                taskId = taskConfirmation.getId() + "";
            }

            return ApiResponse.builder()
                    .data(SarokhTaskConfirmDTO.builder()
                            .driverId(task.getDriverId())
                            .driverName(task.getDriverName())
                            .confirmationId(taskId)
                            .giveShipments(true)
                            .receiveShipments(true)
                            .payCOD(true)
                            .pendingCOD(true)
                            .reportIssues(true)
                            .build())
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.NOT_FOUND.value())
                .message("Unable to request task info. Please try again.")
                .build();

    }


    public ApiResponse searchShipmentByTrackingNumber(String trackingNumber){

        Optional<ShipmentOrder> shipment = orderRepository.findByOrderId(trackingNumber);

        if (shipment.isPresent()){
            ShipmentOrder shipmentOrder = shipment.get();
            ShipmentOrderItem shipmentOrderItem = shipmentOrder.getShipmentOrderItems().get(0);
            Double amount = shipmentOrderItem.getShipmentBilledAmount();
            if (shipmentOrderItem.getPaymentType().equals(PaymentTypeEnum.COD.name())){
                amount = shipmentOrderItem.getCodAmount();
            }
            ShipmentOrderDTO mobileShipmentDTO = ShipmentOrderDTO.builder()
                    .id(shipmentOrder.getId())
                    .trackingNo(shipmentOrder.getOrderId())
                    .mobile(shipmentOrderItem.getContact())
                    .shipmentNo(shipmentOrder.getOrderId())
                    .orderType(shipmentOrderItem.getPaymentType())
                    .pickupDatetime(new Date().toString())
                    .shipperId(shipmentOrder.getShipperId())
                    .deliveryCharges(shipmentOrderItem.getShipmentDeliveryCharges())
                    .billedAmount(amount)
                    .receiverName(shipmentOrderItem.getReceiverName())
                    .shipmentReceived(false)
                    .build();
            return ApiResponse.builder()
                    .data(mobileShipmentDTO)
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .status(HttpStatus.OK.value())
                    .build();
        } else {
            return ApiResponse.builder()
                    .data(null)
                    .message(ApplicationMessagesUtil.INVALID_TRACKING_NUMBER)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

    }

    public ApiResponse verifyTrackingNumberAndReturnShipper(String trackingNumber){

        Optional<ShipmentOrder> shipment = orderRepository.findByOrderId(trackingNumber);

        if (shipment.isPresent()){
            Optional<Shipper> shipper = shipperRepository.findById(shipment.get().getShipperId());
            if (shipper.isPresent()) {
                return ApiResponse.builder()
                        .data(MobileShipperInfoDTO.builder()
                                .name(shipper.get().getFirstName() + " " + shipper.get().getLastName())
                                .shipperId(shipper.get().getId())
                                .build())
                        .message(ApplicationMessagesUtil.SUCCESS)
                        .status(HttpStatus.OK.value())
                        .build();
            }
        }

        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.INVALID_TRACKING_NUMBER)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public ApiResponse handoverReceivedShipment(HandoverReceivedShipmentDTO handoverReceivedShipment){

        Optional<ShipmentOrder> order = orderRepository.findByOrderId(handoverReceivedShipment.getShipmentNo());

        if (order.isPresent()) {

            ShipmentOrderItem orderItem = null;

            if (order.get().getShipmentOrderItems() == null || order.get().getShipmentOrderItems().size() == 0 ){
                System.out.println("updating order = " + order.get().getOrderId());
                Optional<ShipmentOrderItem> shipmentOrderItem = shipmentOrderItemRepository.findByTrackingNumber(order.get().getOrderId());
                if (shipmentOrderItem.isPresent()){
                    orderItem = shipmentOrderItem.get();
                }
            } else {
                orderItem = order.get().getShipmentOrderItems().get(0);
            }

            if (orderItem != null) {

                if (order.get().getStatus().equals(OrderDeliveryStatusEnum.Pending.name()) || order.get().getAssignTo().equals(ShipmentAssigneeEnum.Shipper.name())
                        || order.get().getAssignTo().equals(ShipmentAssigneeEnum.Card.name())){
                    return ApiResponse.builder()
                            .data(null)
                            .message(ApplicationMessagesUtil.UNABLE_TO_DELIVER)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build();
                }

                order.get().setStatus(OrderDeliveryStatusEnum.Delivered.name());
                orderRepository.save(order.get());

                orderItem.setDeliveryStatus(OrderDeliveryStatusEnum.Delivered.name());
                orderItem.setDeliveryDate(new Date());
                orderItem.setSignature(handoverReceivedShipment.getSignature());
                shipmentOrderItemRepository.save(orderItem);

                if (orderItem.getPaymentType().equals(PaymentTypeEnum.COD.name())) {
                    PaymentTransaction transaction = new PaymentTransaction();
                    transaction.setAmountPaid(orderItem.getCodAmount());
                    transaction.setDetails("Paid COD to Sarokh Point");
                    transaction.setTransactionFrom(handoverReceivedShipment.getReceiverName());
                    //transaction.setTransactionFromId();
                    transaction.setTransactionToId(order.get().getDeliveryLocationId());
                    transaction.setTransactionTo(order.get().getDeliveryLocationDetail());

                    if (order.get().getDeliveryLocationId() != null) {

                        Optional<DealerPoint> point = dealerPointRepository.findById(order.get().getDeliveryLocationId());

                        if (point.isPresent()) {
                            transaction.setUserId(point.get().getUserId());
                        }
                    }

                    paymentTransactionRepository.save(transaction);
                }

                updateTaskOfReceiveShipment(orderItem.getTrackingNumber(), null, null);
            }

            return ApiResponse.builder()
                    .data(null)
                    .message(ApplicationMessagesUtil.SHIPMENT_HANDED_OVER_SUCCESSFULLY)
                    .status(HttpStatus.OK.value())
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.INVALID_INFO)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }


    public ApiResponse reportIssueComplaint(ReportIssueDTO reportIssueDTO){

        Optional<ShipmentOrder> order = orderRepository.findByOrderId(reportIssueDTO.getTrackingNumber());

        if (order.isPresent()){
            order.get().setStatus(OrderDeliveryStatusEnum.Missing.name());
            orderRepository.save(order.get());
            order.get().getShipmentOrderItems().get(0).setDeliveryStatus(OrderDeliveryStatusEnum.Missing.name());
            shipmentOrderItemRepository.save(order.get().getShipmentOrderItems().get(0));

            ShipmentIssueReport report = new ShipmentIssueReport();
            report.setComplaintAgainst(reportIssueDTO.getComplaintAgainst());
            report.setComplaintAgainstName(reportIssueDTO.getComplaintAgainstName());
            report.setCompliantDescription(reportIssueDTO.getComplaintComments());
            report.setReportedBy(PickupTypeEnum.DealerPoint.getPickupTypeName());
            report.setTrackingNumber(reportIssueDTO.getTrackingNumber());
            issueReportRepository.save(report);

            return ApiResponse.builder()
                    .data("ComplaintId = " + report.getId())
                    .message(ApplicationMessagesUtil.REPORT_COMPLAINT_SUCCESSFULLY)
                    .status(HttpStatus.OK.value())
                    .build();
        }


        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.INVALID_INFO)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    public ApiResponse confirmShipperReceiveShipments(MobileShippersListDTO shippersListDTO){

        if (shippersListDTO.getShipmentList() != null && shippersListDTO.getShipmentList().size() > 0){
            for (String trackingNo: shippersListDTO.getShipmentList()){
                Optional<ShipmentOrder> order = orderRepository.findByOrderId(trackingNo);
                if (order.isPresent()) {

                    if (shippersListDTO.getDealerId() != null) {
                        order.get().setAssignToId(shippersListDTO.getDealerId());
                        order.get().setAssignTo(PickupTypeEnum.DealerPoint.getPickupTypeName());
                        updateTaskOfReceiveShipment(trackingNo, null, shippersListDTO.getDealerId());
                    } else if (shippersListDTO.getDriverId() != null){
                        order.get().setAssignToId(shippersListDTO.getDriverId());
                        order.get().setAssignTo("Driver");
                        updateTaskOfReceiveShipment(trackingNo, shippersListDTO.getDriverId(), null);
                    }
                    orderRepository.save(order.get());
                }
            }

            return ApiResponse.builder()
                    .data(null)
                    .message("Received shipments confirmed successfully.")
                    .status(HttpStatus.OK.value())
                    .build();

        }

        return ApiResponse.builder()
                .data(null)
                .message("Received shipments not found.")
                .status(HttpStatus.NOT_FOUND.value())
                .build();

    }

    public ApiResponse createMobileShipment(MobileCreateShipmentDTO mobileShipmentDTO){

        DealerPoint point = getDealerPointByDealerId(mobileShipmentDTO.getDealerId());
        User user = userService.getGuestUserForShipper();

        Shipper shipper = new Shipper();
        shipper.setShipperType("WalkIn Customer");
        shipper.setFirstName(mobileShipmentDTO.getSenderName());
        shipper.setAddress(mobileShipmentDTO.getSenderAddress());
        shipper.setContact(mobileShipmentDTO.getSenderContact());
        shipper.setNicNumber(mobileShipmentDTO.getSenderIdNumber());
        shipper.setNicFile(mobileShipmentDTO.getSenderIdFile());
        shipper.setUserId(user != null ? user.getUserId() : 0);
        shipperRepository.save(shipper);

        ShipmentOrder shipmentOrder = new ShipmentOrder();
        shipmentOrder.setOrderId(orderService.generateNewOrderId(shipper.getId()));
        shipmentOrder.setShipperId(shipper.getId());
        shipmentOrder.setShipFromCity(point.getCity());
        shipmentOrder.setPickupLocation(PickupTypeEnum.DealerPoint.getPickupTypeName());
        shipmentOrder.setPickupLocationId(point.getId());
        shipmentOrder.setPickupLocationDetail(point.getDealerPointName());

        if (mobileShipmentDTO.getDeliveryType().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())){
            shipmentOrder.setDeliveryLocation(PickupTypeEnum.DealerPoint.getPickupTypeName());
            shipmentOrder.setDeliveryLocationId(mobileShipmentDTO.getDeliverySarokhPointId());
        } else if (mobileShipmentDTO.getDeliveryType().equals(DeliveryTypeEnum.ReceiverAddress.getDeliveryTypeName())){
            shipmentOrder.setDeliveryLocation(DeliveryTypeEnum.LastMile.getDeliveryTypeName());
        }

        shipmentOrder.setShipToCity(mobileShipmentDTO.getReceiverCity());
        shipmentOrder.setAssignTo(ShipmentAssigneeEnum.SarokhPoint.name());
        shipmentOrder.setAssignToId(mobileShipmentDTO.getDealerId());
        shipmentOrder.setAssignToDetail(point.getDealerPointName());
        shipmentOrder.setStatus(OrderDeliveryStatusEnum.Pending.name());
        shipmentOrder.setReceiverType(ShipmentTypeEnum.SingleReceiver.name());

        orderRepository.save(shipmentOrder);

        ShipmentOrderItem shipmentOrderItem = new ShipmentOrderItem();
        shipmentOrderItem.setTrackingNumber(shipmentOrder.getOrderId());
        shipmentOrderItem.setShipmentOrderId(shipmentOrder.getId());
        shipmentOrderItem.setReceiverName(mobileShipmentDTO.getReceiverName());
        shipmentOrderItem.setAddress(mobileShipmentDTO.getReceiverAddress());
        shipmentOrderItem.setContact(mobileShipmentDTO.getReceiverContact());
        shipmentOrderItem.setShipmentContent(mobileShipmentDTO.getShipmentContent());
        shipmentOrderItem.setShipmentType(mobileShipmentDTO.getShipmentType());
        shipmentOrderItem.setWeight(mobileShipmentDTO.getShipmentWeight());
        shipmentOrderItem.setPaymentType(mobileShipmentDTO.getPaymentMethod());

        String additionalServices = "";

        if (mobileShipmentDTO.isGiftPackaging()){
            additionalServices += AdditionalServicesEnum.GiftPackaging.getServiceName() + ",";
        }
        if (mobileShipmentDTO.isInsurance()){
            additionalServices += AdditionalServicesEnum.Insurance.getServiceName() + ",";
        }
        if (mobileShipmentDTO.isNormalPackaging()){
            additionalServices += AdditionalServicesEnum.NormalPackaging.getServiceName() + ",";
        }

        shipmentOrderItem.setAdditionalServices(additionalServices);
        shipmentOrderItem.setShipmentDeliveryCharges(mobileShipmentDTO.getDeliveryCharges());
        shipmentOrderItem.setShipmentBilledAmount(mobileShipmentDTO.getTotalAmount());
        shipmentOrderItem.setCodAmount(mobileShipmentDTO.getCodAmount());
        shipmentOrderItem.setReceivedFromShipper(ShipmentReceivedStatusEnum.Yes.name());
        shipmentOrderItem.setReceiverConfirmation(ReceiverConfirmationEnum.No.name());
        shipmentOrderItem.setDeliveryStatus(OrderDeliveryStatusEnum.Pending.name());

        String qrcodeFile = FILE_UPLOAD_DIR + QR_CODE_DIRECTORY + shipmentOrderItem.getTrackingNumber() + ".png";
        String generatedFile = QRCodeGenerator.generateQRCode(shipmentOrderItem.getTrackingNumber(), qrcodeFile);
        shipmentOrderItem.setQRCode(WEB_APP_URL + QR_CODE_DIRECTORY + generatedFile);

        String barcodeFile = FILE_UPLOAD_DIR + BAR_CODE_DIRECTORY + shipmentOrderItem.getTrackingNumber() + ".png";
        String barCodeGeneratedFile = BarCodeGenerator.generateBarCode(shipmentOrderItem.getTrackingNumber(), barcodeFile);
        if (null != barCodeGeneratedFile) {
            shipmentOrderItem.setBarCode(barCodeGeneratedFile.replaceAll(FILE_UPLOAD_DIR, WEB_APP_URL));
        }

        shipmentOrderItemRepository.save(shipmentOrderItem);

        return ApiResponse.builder()
                .data(shipmentOrder.getOrderId())
                .message(ApplicationMessagesUtil.ORDER_SUCCESSFULLY_CREATED)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getBill(SarokhPayDTO billDTO){

        return ApiResponse.builder()
                .data(25.5)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse paySarokhPayment(SarokhPayDTO billDTO){

        return ApiResponse.builder()
                .data("123")
                .message("Payment made successfully")
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getDealerWalletDetail(Integer dealerId){

        return ApiResponse.builder()
                .data(walletService.getDealerWalletDetail())
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public void updateTaskOfReceiveShipment(String trackingNo, Integer driverId, Integer dealerId){
        Iterable<SarokhTask> tasks = null;

        if (dealerId != null){
            tasks = taskRepository.getDealerPointSarokhTasks(dealerId);
        } else if (driverId != null){
            tasks = taskRepository.getDriverSarokhTasks(driverId);
        } else {
            tasks = taskRepository.findByStatus(DispatchTripStatusEnum.Active.name());
        }

        if (tasks != null){
            Iterator<SarokhTask> iterator = tasks.iterator();
            Integer tripId = null;
            while (iterator.hasNext()){
                SarokhTask task = iterator.next();
                if ( (ApplicationUtil.isNotNullAndEmpty(task.getReceiveShipments()) && task.getReceiveShipments().indexOf(trackingNo) >= 0) ||
                        (ApplicationUtil.isNotNullAndEmpty(task.getGiveShipments()) && task.getGiveShipments().indexOf(trackingNo) >= 0) ){
                    task.setStatus(DispatchTripStatusEnum.Completed.name());
                    taskRepository.save(task);
                    tripId = task.getTripId();
                }
            }

            if (tripId != null){
                updateTripStatus(tripId);
            }
        }
    }

    private void updateTripStatus(Integer tripId){

        Iterable<SarokhTask> tasks = taskRepository.getSarokhTasksByTripId(tripId);
        Iterator<SarokhTask> iterator = tasks.iterator();
        boolean allCompleted = true;

        while (iterator.hasNext()){
            SarokhTask task = iterator.next();
            if (task.getStatus().equals(DispatchTripStatusEnum.Active.name())){
                allCompleted = false;
                break;
            }
        }

        if (allCompleted){
            Optional<DispatchTrip> trip = tripRepository.findById(tripId);
            if (trip.isPresent()){
                trip.get().setTripStatus(DispatchTripStatusEnum.Completed.name());
                tripRepository.save(trip.get());
            }
        }

    }

    private DealerPoint getDealerPointByDealerId(Integer pointId){
        Optional<DealerPoint> dealerPoint =  dealerPointRepository.findById(pointId);
        if (dealerPoint.isPresent()){
            return dealerPoint.get();
        }

        return null;
    }

}
