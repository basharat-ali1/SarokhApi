package net.sarokh.api.service.mobile;

import net.sarokh.api.dao.*;
import net.sarokh.api.model.DateTimeDTO;
import net.sarokh.api.model.GetBillDetailDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.*;
import net.sarokh.api.model.mobile.*;
import net.sarokh.api.service.WalletService;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.ApplicationUtil;
import net.sarokh.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MobileDriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DispatchTripRepository dispatchTripRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SarokhTaskConfirmationRepository taskConfirmationRepository;

    @Autowired
    private SarokhTaskRepository taskRepository;

    @Autowired
    private ShipperWarehouseRepository warehouseRepository;

    @Autowired
    private DealerPointRepository dealerPointRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private VehicleRepository vehicleRepository;

    // Done
    public ApiResponse getMobileDriverDashboard(Integer driverId){
        List<DispatchTrip> trips = dispatchTripRepository.findAllByDriverIdAndTripStatus(driverId, DispatchTripStatusEnum.Completed.name());
        Optional<Driver> driver = driverRepository.findById(driverId);
        Vehicle vehicle = driver.isPresent() ? driver.get().getVehicle() : null;
        WalletsBalanceDTO wallets = getDriverWalletBalance(driverId);

        Integer delivered = 0;
        Integer picked = 0;
        Integer totalTrips = 0;

        Iterator<DispatchTrip> iterator = trips.iterator();

        while (iterator.hasNext()){
            DispatchTrip trip = iterator.next();
            delivered += trip.getDeliveryShipments();
            picked += trip.getPickupShipments();
            totalTrips ++;
        }

        MobileDriverDashboardDTO dashboardDTO = MobileDriverDashboardDTO.builder()
                .droppedOff(delivered)
                .pickedUp(picked)
                .trips(totalTrips)
                .cargoCapacity(vehicle != null ? vehicle.getCargoCapacity() : "0 Kg")
                .vehicle(vehicle != null ? vehicle.getName() : "No vehicle")
                .collectionWalletBalance(wallets.getCollectionWalletBalance())
                .compensationWalletBalance(wallets.getCompensationWalletBalance())
                .availableShipments(0)
                .nextStopDistance(0.0)
                .completedStops(0)
                .remainingStops(0)
                .build();

        return ApiResponse.builder()
                .data(dashboardDTO)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    // Done
    public ApiResponse getDriverSarokhTask(Integer driverId){
        List<DriverSarokhTaskDTO> list = new ArrayList<>();
        Iterable<SarokhTask> sarokhTasks = taskRepository.getDriverSarokhTasks(driverId);
        Iterator taskIterator = sarokhTasks.iterator();

        while(taskIterator.hasNext()) {
            SarokhTask task = (SarokhTask) taskIterator.next();
            String receiverName = null;
            String receiverType = null;

            if (task.getStopType().equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName())){
                Optional<ShipperWarehouse> warehouse = warehouseRepository.findById(task.getLocationId());
                if (warehouse.isPresent()){
                    receiverName = warehouse.get().getManagerName();
                }
                receiverType = "Attendant";
            } else if (task.getStopType().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())){
                Object name = dealerPointRepository.getConcernedPersonNameByPointId(task.getLocationId());
                if (name != null){
                    receiverName = name.toString();
                }
                receiverType = "Operator";
            } else if (task.getStopType().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())){
                String id = task.getGiveShipments();
                System.out.println("id=" + id);
                Optional<ShipmentOrder> order = orderRepository.findByOrderId(id);
                if (order.isPresent()){
                    receiverName = order.get().getShipmentOrderItems().get(0).getReceiverName();
                }
                receiverType = "Receiver";
            }

            String[] giveShipmentsArray = task.getGiveShipments() != null ? task.getGiveShipments().split(",") : null;
            String[] receiveShipmentsArray = task.getReceiveShipments() != null ? task.getReceiveShipments().split(",") : null;

            list.add(DriverSarokhTaskDTO.builder()
                    .id(task.getId())
                    .stopType(task.getStopType())
                    .stopTypeId(task.getLocationId())
                    .receiverType(receiverType)
                    .receiverName(receiverName)
                    .latitude(task.getLocationLatitude())
                    .longitude(task.getLocationLongitude())

                    .dealerId(task.getLocationId())
                    .allShipmentsGiven(false)
                    .allShipmentsGiven(true)
                    .isCODPaid(false)
                    .delivered(false)
                    .locationName(task.getLocation())
                    .tripId(task.getTripId())
                    .giveShipments(giveShipmentsArray != null ? giveShipmentsArray.length : 0)
                    .receiveShipments(receiveShipmentsArray != null ? receiveShipmentsArray.length : 0 )
                    .payCOD(task.getCodAmount())
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    // Done
    public ApiResponse getMobileDriverTrips(Integer driverId){
        List<MobileDriverTripsDTO> list = new ArrayList<>();

        Iterable<?> trips = dispatchTripRepository.findDriverTrips(driverId);
        Iterator<?> iterator = trips.iterator();

        while (iterator.hasNext()){
            Object[] trip = (Object[]) iterator.next();
            Object date = trip[0];
            Object startTime = trip[1];
            Object endTime = trip[2];
            Object codAmount = trip[3];
            Object totalStops = trip[4];

            list.add(MobileDriverTripsDTO.builder()
                    .date(date != null ? date.toString() : null)
                    .startTime(startTime != null ? startTime.toString() : null)
                    .endTime(endTime != null ? endTime.toString() : null)
                    .totalStops(totalStops != null ? Integer.parseInt(totalStops.toString()) : null)
                    .paymentCollection(codAmount != null ? Double.parseDouble(codAmount.toString()) : null)
                    .tripId(trip[5] != null ? Integer.parseInt(trip[5].toString()) : 0)
                    .totalPickups(trip[6] != null ? Integer.parseInt(trip[6].toString()) : 0)
                    .totalDeliveries(trip[7] != null ? Integer.parseInt(trip[7].toString()) : 0)
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    // Done
    public ApiResponse getGiveShipmentsDetail(Integer pointId){
        List<ReceiveShipmentsDetailDTO> list = new ArrayList<>();
        Optional<DealerPoint> point = dealerPointRepository.findById(pointId);

        if (point.isPresent() ) {

            Iterable<SarokhTask> sarokhTask = taskRepository.getDealerPointSarokhTasks(point.get().getId());
            Iterator<SarokhTask> iterator = sarokhTask.iterator();

            while(iterator.hasNext()) {
                SarokhTask task = iterator.next();
                // Driver give shipments will be Dealer receive shipments
                String[] giveShipmentsArray  = task.getGiveShipments() != null ? task.getGiveShipments().split(",") : null;

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
    public ApiResponse getDealerPointSummaryForDriver(Integer taskId){

        Optional<SarokhTask> task = taskRepository.findById(taskId);

        if (!task.isPresent()){
            return ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("No task at this dealer point")
                    .build();
        }

        String[] receiveShipmentsArray = ApplicationUtil.isNotNullAndEmpty(task.get().getReceiveShipments()) ? task.get().getReceiveShipments().split(",") : null;
        String[] giveShipmentsArray = ApplicationUtil.isNotNullAndEmpty(task.get().getGiveShipments()) ? task.get().getGiveShipments().split(",") : null;

        Integer dealerId = null;
        if (task.get().getLocationId() != null) {
            Optional<DealerPoint> point = dealerPointRepository.findById(task.get().getLocationId());
            dealerId = point.isPresent() ? point.get().getId() : task.get().getLocationId();
        }

        return ApiResponse.builder()
                .data(SarokhTaskRequestDTO.builder()
                        .taskId(taskId)
                        .receiveShipments( receiveShipmentsArray != null ? receiveShipmentsArray.length : 0)
                        .giveShipments(giveShipmentsArray != null ? giveShipmentsArray.length : 0)
                        .payCOD(task.get().getCodAmount())
                        .driverId(task.get().getDriverId() +"")
                        .driverName(task.get().getDriverName())
                        .dealerId(dealerId)
                        .build())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();



    }

    // Done
    public ApiResponse confirmSarokhTask(Integer driverId){

        Optional<SarokhTaskConfirmation> taskConfirmation = taskConfirmationRepository.getSarokhTaskConfirmationByDriverId(driverId);

        if (taskConfirmation.isPresent()){
            Optional<Driver> driver = driverRepository.findById(taskConfirmation.get().getDriverId());

            return ApiResponse.builder()
                    .data(SarokhTaskDriverConfirmDTO.builder()
                            .driverId(taskConfirmation.get().getDriverId())
                            .driverName(driver.get().getFirstName() + " " + driver.get().getLastName())
                            .confirmationId(taskConfirmation.get().getId())
                            .giveShipments(taskConfirmation.get().getGiveShipments())
                            .receiveShipments(taskConfirmation.get().getReceiveShipments())
                            .payCOD(taskConfirmation.get().getReceiveCod())
                            .pendingCOD(taskConfirmation.get().getPendingCod())
                            .reportIssues(false)
                            .build())
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    // Done
    public ApiResponse submitDriverTaskConfirmation(SarokhTaskDriverConfirmDTO confirmDTO){

        Optional<SarokhTaskConfirmation> taskConfirmation = taskConfirmationRepository.getSarokhTaskConfirmationByDriverId(confirmDTO.getDriverId());

        System.out.println("\nTask confirmation driver : " + taskConfirmation.isPresent());

        if (taskConfirmation.isPresent()){
            taskConfirmation.get().setGiveShipments(confirmDTO.getGiveShipments());
            taskConfirmation.get().setReceiveShipments(confirmDTO.getReceiveShipments());
            taskConfirmation.get().setPendingCod(confirmDTO.getPendingCOD());
            taskConfirmation.get().setReceiveCod(confirmDTO.getPayCOD());
            taskConfirmation.get().setStatus(DispatchTripStatusEnum.Completed.name());

            taskConfirmationRepository.save(taskConfirmation.get());
            System.out.println("Task confirmation completed : " + taskConfirmation.get().getStatus());

            // Complete sarokh Task.
            Optional<SarokhTask> task = taskRepository.findById(taskConfirmation.get().getSarokhTaskId());

            if (task.isPresent()){
                task.get().setStatus(DispatchTripStatusEnum.Completed.name());
                taskRepository.save(task.get());
                System.out.println("Task saved task id = " + taskConfirmation.get().getSarokhTaskId() + ", status = " + task.get().getStatus());

                String[] receiveShipments = task.get().getReceiveShipments() != null ? task.get().getReceiveShipments().split(",") : null;

                if (receiveShipments != null && receiveShipments.length > 0){
                    for (int i=0;i<receiveShipments.length;i++){
                        Optional<ShipmentOrder> order = orderRepository.findByOrderId(receiveShipments[i]);
                        if (order.isPresent()){
                            order.get().setAssignTo("Driver");
                            order.get().setAssignToId(taskConfirmation.get().getDriverId());
                            orderRepository.save(order.get());
                        }
                    }
                } else {
                    System.out.println("No Receive shipments for task id = " + taskConfirmation.get().getSarokhTaskId());
                }

            } else {
                System.out.println("No Task in confirmation for task id = " + taskConfirmation.get().getSarokhTaskId());
            }
        } else {
            System.out.println("No Task confirmation for driver");
            return ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("No Task confirmation for driver")
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SAROKH_TASK_SUBMITTED)
                .build();
    }

    // Done
    public ApiResponse getReceiveShipmentsDetail(Integer driverId, Integer pointId){
        List<ReceiveShipmentsDetailDTO> list = new ArrayList<>();

        Optional<DealerPoint> point = dealerPointRepository.findById(pointId);

        if (point.isPresent()) {

            Iterable<SarokhTask> taskList = taskRepository.findByDealerPointIdAndDriverIdAAndStatus(point.get().getId(), driverId);
            Iterator iterator = taskList.iterator();

            while (iterator.hasNext()) {
                SarokhTask task = (SarokhTask) iterator.next();
                String[] receiveShipmentsArray = task.getReceiveShipments().split(",");

                System.out.println("receive shipment=" + receiveShipmentsArray.length);

                if (receiveShipmentsArray != null) {

                    for (int i = 0; i < receiveShipmentsArray.length; i++) {

                        Optional<ShipmentOrder> shipment = orderRepository.findByOrderId(receiveShipmentsArray[i]);
                        if (shipment.isPresent()) {
                            String receivedStatus = "No";
                            if (shipment.isPresent() && shipment.get().getAssignTo() != null && shipment.get().getAssignTo().equals("Driver")) {
                                receivedStatus = "Yes";
                            }

                            list.add(ReceiveShipmentsDetailDTO.builder()
                                    .trackingNo(shipment.get().getShipmentOrderItems().get(0).getTrackingNumber())
                                    .receiverName(shipment.get().getShipmentOrderItems().get(0).getReceiverName())
                                    .billedAmount(shipment.get().getShipmentOrderItems().get(0).getShipmentBilledAmount())
                                    .receivedStatus(receivedStatus)
                                    .build());
                        } else {
                            System.out.println("receive shipment.  Item not found = " + receiveShipmentsArray[i]);
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


    public ApiResponse getShipperWarehouseShipmentsDetail(Integer taskId){

        Optional<SarokhTask> task = taskRepository.findById(taskId);

        if (task.isPresent()){

            String[] receiveShipmentsArray =  task.get().getReceiveShipments().split(",");
            //String giveShipmentsArray[] =  task.get().getGiveShipments().split(",");

            return ApiResponse.builder()
                    .data(SarokhTaskRequestDTO.builder()
                            .taskId(task.get().getId())
                            .tripId(task.get().getTripId())
                            .receiveShipments(receiveShipmentsArray.length)
                            .returnShipments(0)
                            .driverId(task.get().getDriverId() +"")
                            .driverName(task.get().getDriverName())
                            .build())
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.NOT_FOUND.value())
                .message("No shipments at shipper warehouse.")
                .build();
    }


    // Progress
    // TODO: Mock Data returned. Will be changed
    public ApiResponse getDriverReceiveCOD(SearchDriverReceiveCodDTO codDTO){



        return ApiResponse.builder()
                .data(DriverReceiveCodDTO.builder()
                        .dealerId(15)
                        .dealerName("Ali khan")
                        .businessName("Dealer Point 1")
                        .billedCOD(5000)
                        .build())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse submitDriverReceiveCOD(DriverReceiveCodPaymentDTO codDTO){

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.AMOUNT_RECEIVED)
                .build();
    }

    public ApiResponse addDriverVehicle(AddVehicleDTO driverVehicle){

        Optional<Driver> driver = driverRepository.findById(driverVehicle.getDriverId());

        if (driver.isPresent()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setName(driverVehicle.getVehicleName());
            vehicle.setRegistrationNumber(driverVehicle.getRegistrationNumber());
            vehicle.setRegistrationFile(driverVehicle.getRegistrationFile());
            vehicle.setModel(driverVehicle.getVehicleModel());
            vehicle.setMake(driverVehicle.getMake());
            vehicle.setCurrentMileage(driverVehicle.getCurrentMileage());
            vehicle.setRegistrationYear(driverVehicle.getRegistrationYear());
            vehicle.setType(driverVehicle.getType());
            vehicle.setCargoCapacity(driverVehicle.getCargoCapacity());
            vehicle.setOwner(VehicleOwnerEnum.OperatorOwned.name());
            vehicle.setProductionYear(driverVehicle.getProductionYear());
            vehicle.setStatus("Active");

            vehicleRepository.save(vehicle);

            driver.get().setVehicleId(vehicle.getId());
            driverRepository.save(driver.get());

            return ApiResponse.builder()
                    .data(vehicle)
                    .status(HttpStatus.OK.value())
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .build();
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ApplicationMessagesUtil.INVALID_INFO)
                .build();

    }

    public ApiResponse driverReceiveAmount(DriveReceiveAmountDTO receiveAmountDTO){

        Optional<SarokhTask> task = taskRepository.findById(receiveAmountDTO.getTaskId());

        if (task.isPresent() && task.get().getCodAmount() != null && receiveAmountDTO.getAmount() != null) {
            if (task.get().getCodAmount().doubleValue() == receiveAmountDTO.getAmount().doubleValue() &&
                    task.get().getAmountReceived() == null) {

                task.get().setAmountReceived(true);
                taskRepository.save(task.get());

                Optional<Wallet> driverWallet = walletService.getDriverCollectionWallet(receiveAmountDTO.getDriverId());

                if (driverWallet.isPresent()){
                    driverWallet.get().setCurrentBalance(driverWallet.get().getCurrentBalance() + task.get().getCodAmount().doubleValue());
                    walletService.addWallet(driverWallet.get());
                }

                return ApiResponse.builder()
                        .data(null)
                        .status(HttpStatus.OK.value())
                        .message(ApplicationMessagesUtil.AMOUNT_RECEIVED)
                        .build();
            } else {
                return ApiResponse.builder()
                        .data(null)
                        .status(HttpStatus.OK.value())
                        .message(ApplicationMessagesUtil.AMOUNT_ALREADY_RECEIVED)
                        .build();
            }
        }

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ApplicationMessagesUtil.INVALID_INFO)
                .build();

    }

    public WalletsBalanceDTO getDriverWalletBalance(Integer driverId){
        Double compensationBalance = 0.0;
        Double collectionBalance = 0.0;

        Iterable<Wallet> wallets = walletService.getUserWalletsList(GetBillDetailDTO.builder()
                .userId(driverId)
                .UserType(UserRolesEnum.Driver.name())
                .build());
        Iterator<Wallet> walletIterator = wallets.iterator();

        while (walletIterator.hasNext()){
            Wallet wallet = walletIterator.next();
            if(wallet.getWalletType().equals(WalletTypeEnum.DriverCollection.getWalletType())){
                collectionBalance = wallet.getCurrentBalance();
            } else if(wallet.getWalletType().equals(WalletTypeEnum.DriverCompensation.getWalletType())){
                compensationBalance = wallet.getCurrentBalance();
            }
        }

        return WalletsBalanceDTO.builder()
                .userType(UserRolesEnum.Driver.name())
                .userTypeId(driverId)
                .collectionWalletBalance(collectionBalance)
                .compensationWalletBalance(compensationBalance)
                .build();
    }

    private SarokhTaskRequestDTO getSarokhTaskForDriver(Integer pointId, Integer driverId){

        //Iterable<?> shipmentList = orderRepository.getDealerSarokhTask(pointId);
        Iterable<SarokhTask> taskList = taskRepository.findByDealerPointIdAndDriverIdAAndStatus(pointId, driverId);
        Iterator iterator = taskList.iterator();
        while (iterator.hasNext()) {
            SarokhTask task = (SarokhTask) iterator.next();

            String[] receiveShipmentsArray =  task.getReceiveShipments().split(",");
            String[] giveShipmentsArray =  task.getGiveShipments().split(",");

            return SarokhTaskRequestDTO.builder()
                    .giveShipments(receiveShipmentsArray.length)
                    .receiveShipments(giveShipmentsArray.length)
                    .payCOD(task.getCodAmount())
                    .driverId(task.getDriverId() +"")
                    .driverName(task.getDriverName())

                    .build();
        }

        return null;
    }

}
