package net.sarokh.api.service;

import net.sarokh.api.dao.*;
import net.sarokh.api.model.DateTimeDTO;
import net.sarokh.api.model.admin.TripListingDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.*;
import net.sarokh.api.model.trip.CreateTripDetailsDTO;
import net.sarokh.api.model.trip.CreateTripSearchResultsDTO;
import net.sarokh.api.model.trip.TripDetailsDTO;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DispatchTripService {

    @Autowired
    private DispatchTripRepository repository;

    @Autowired
    private DispatchTripDetailRepository tripDetailRepository;

    @Autowired
    private DealerPointRepository dealerPointRepository;

    @Autowired
    private DealerPointService dealerPointService;

    @Autowired
    private SarokhWarehouseRepository sarokhWarehouseRepository;

    @Autowired
    private ShipmentOrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private SarokhTaskRepository sarokhTaskRepository;

    @Autowired
    private ShipperWarehouseRepository shipperWarehouseRepository;

    public ApiResponse createTrip(CreateTripDetailsDTO tripDetailsDTO){

        Optional<SarokhWarehouse> warehouse = sarokhWarehouseRepository.findById(tripDetailsDTO.getWarehouseId());

        // Store all shipment orders and then update with dispatch order id;
        List<ShipmentOrder> shipmentOrders = new ArrayList<>();
        DispatchTrip trip = new DispatchTrip();
        Optional<Driver> driver = driverRepository.findById(tripDetailsDTO.getDriverId());

        trip.setDispatchDatetime(new Date());
        trip.setDriverId(tripDetailsDTO.getDriverId());
        trip.setVehicleId(tripDetailsDTO.getVehicleId());

        if (driver.isPresent()) {
            trip.setDriverName(driver.get().getFirstName() + " " + driver.get().getLastName());
        }

        trip.setStartPoint(warehouse.get().getName());
        trip.setTripStatus(DispatchTripStatusEnum.Active.name());
        trip.setCodCollection(tripDetailsDTO.getCODCollection());
        repository.save(trip);

        int totalPickups = 0;
        int totalDeliveries = 0;
        List<DispatchTripDetail> tripDetailList = new ArrayList<>();

        for (TripDetailsDTO shipment: tripDetailsDTO.getShipmentsList()){
            DispatchTripDetail tripDetail = new DispatchTripDetail();
            double codCollection = 0.0;
            Optional<ShipmentOrder> shipmentOrder = orderRepository.findByOrderId(shipment.getShipmentId());
            tripDetail.setShipmentOrderId(shipment.getShipmentId());

            //tripDetail.setCodCollection(shipment.getCODCollection());
            tripDetail.setDispatchTripId(trip.getId());
            if (shipment.getCODCollection() != null){
                tripDetail.setPaymentType(PaymentTypeEnum.COD.name());
                codCollection += shipment.getCODCollection();
            } else {
                tripDetail.setPaymentType(PaymentTypeEnum.Prepaid.name());
            }
            tripDetail.setPickupDelivery(shipment.getDeliveryPickup());

            if (shipment.getDeliveryPickup().equals(PickupDeliveryEnum.Pickup.name())){
                totalPickups++;
                tripDetail.setPickupLocation(shipmentOrder.get().getPickupLocation());
                tripDetail.setPickupLocationId(shipmentOrder.get().getPickupLocationId());
                tripDetail.setDeliveryLocation(shipmentOrder.get().getDeliveryLocation());
                tripDetail.setDeliveryLocationId(shipmentOrder.get().getDeliveryLocationId());

                if (shipmentOrder.get().getPickupLocation().equals(PickupTypeEnum.DealerPoint.getPickupTypeName()) && shipmentOrder.get().getAssignTo() != null &&
                        shipmentOrder.get().getAssignTo().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())){
                    tripDetail.setPickupLocationId(shipmentOrder.get().getAssignToId());
                }

                if (shipmentOrder.get().getPickupLocation().equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName())){
                    tripDetail.setStopType(PickupTypeEnum.ShipperWarehouse.getPickupTypeName());
                } else {
                    tripDetail.setStopType(PickupTypeEnum.DealerPoint.getPickupTypeName());
                }
            } else {
                totalDeliveries++;
                tripDetail.setPickupLocation(shipmentOrder.get().getPickupLocation());
                tripDetail.setPickupLocationId(shipmentOrder.get().getPickupLocationId());
                tripDetail.setDeliveryLocation(shipmentOrder.get().getDeliveryLocation());
                tripDetail.setDeliveryLocationId(shipmentOrder.get().getDeliveryLocationId());
                if (shipmentOrder.get().getDeliveryLocation().equals(DeliveryTypeEnum.SarokhPoint.getDeliveryTypeName())){
                    tripDetail.setStopType(PickupTypeEnum.DealerPoint.getPickupTypeName());
                } else if (shipmentOrder.get().getDeliveryLocation().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())){
                    tripDetail.setStopType(DeliveryTypeEnum.LastMile.getDeliveryTypeName());
                }
            }

            if (shipmentOrder.get().getDeliveryLocation() != null && shipmentOrder.get().getDeliveryLocation().equals(DeliveryTypeEnum.PredefinedLocation.getDeliveryTypeName())){
                tripDetail.setAddress(shipmentOrder.get().getShipmentOrderItems().get(0).getAddress());
            } else if(shipmentOrder.get().getDeliveryLocation() != null && shipmentOrder.get().getDeliveryLocation().equals(DeliveryTypeEnum.SarokhPoint.getDeliveryTypeName())){
                if (shipmentOrder.get().getDeliveryLocationId() != null) {
                    Optional<DealerPoint> point = dealerPointRepository.findById(shipmentOrder.get().getDeliveryLocationId());
                    if (point.isPresent()) {
                        tripDetail.setAddress(point.get().getAddress());
                        tripDetail.setDeliveryLocation(point.get().getDealerPointName());
                        tripDetail.setDeliveryLocationId(point.get().getId());
                    }
                }
            } else if (shipmentOrder.get().getDeliveryLocation() != null && shipmentOrder.get().getDeliveryLocation().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName()) &&
                    shipmentOrder.get().getAssignTo() != null && shipmentOrder.get().getAssignTo().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())){
                tripDetail.setPickupLocation(shipmentOrder.get().getAssignTo());
                tripDetail.setPickupLocationId(shipmentOrder.get().getAssignToId());
                tripDetail.setDeliveryLocation(DeliveryTypeEnum.LastMile.getDeliveryTypeName());
            }

            tripDetail.setLocationLatitude(shipmentOrder.get().getShipmentOrderItems().get(0).getLocationLatitude());
            tripDetail.setLocationLongitude(shipmentOrder.get().getShipmentOrderItems().get(0).getLocationLongitude());

            tripDetail.setCodCollection(codCollection);
            //tripDetailRepository.save(tripDetail);

            tripDetailList.add(tripDetail);
            shipmentOrder.get().setDispatchTripId(trip.getId());
            shipmentOrder.get().getShipmentOrderItems().get(0).setDeliveryStatus(OrderDeliveryStatusEnum.Active.name());
            shipmentOrder.get().setStatus(OrderDeliveryStatusEnum.Active.name());
            shipmentOrders.add(shipmentOrder.get());
        }

        trip.setDeliveryShipments(totalDeliveries);
        trip.setPickupShipments(totalPickups);
        trip.setTripDetailItemsList(tripDetailList);

        repository.save(trip);

        orderRepository.saveAll(shipmentOrders);


        createTasksFromTrip(trip);

        return ApiResponse.builder()
                .data("Trip Successfully created.")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    private void createTasksFromTrip(DispatchTrip trip){

        ArrayList<DispatchTripDetail> iteratingList = (ArrayList) trip.getTripDetailItemsList().stream().collect(Collectors.toList());
        String shipmentsAdded = "";
        List<SarokhTask> tasksList = new ArrayList<>();

        //for (DispatchTripDetail detail: trip.getTripDetailItemsList()){
        for (int i=0; i< trip.getTripDetailItemsList().size() ; i++){
            DispatchTripDetail detail = trip.getTripDetailItemsList().get(i);
            if (shipmentsAdded.indexOf(detail.getShipmentOrderId()) >= 0){
                continue;
            }

            SarokhTask task = new SarokhTask();
            task.setDriverId(trip.getDriverId());
            task.setDriverName(trip.getDriverName());
            task.setStatus("Active");
            task.setTripId(trip.getId());
            task.setLocationLatitude(detail.getLocationLatitude());
            task.setLocationLongitude(detail.getLocationLongitude());
            task.setStopType(detail.getStopType());
            String giveShipmentList = "";
            String receiveShipmentList = "";

            // Items will be stored for removal when its Task is created.
            List<DispatchTripDetail> removeList = new ArrayList<>();

            if (detail.getDeliveryLocation() != null && detail.getDeliveryLocation().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())
                    && detail.getPickupDelivery().equals(PickupDeliveryEnum.Delivery.name())){
                task.setCodAmount(detail.getCodCollection());
                task.setAddress(detail.getAddress());
                giveShipmentList = detail.getShipmentOrderId();

                if (task.getStopType().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())){
                    task.setLocationId(detail.getPickupLocationId());
                    task.setLocation(detail.getPickupLocation());
                    task.setReceiveShipments(giveShipmentList);
                } else {
                    task.setGiveShipments(giveShipmentList);
                    task.setLocation(detail.getDeliveryLocation());
                    task.setStopType(DeliveryTypeEnum.LastMile.getDeliveryTypeName());
                }

                removeList.add(detail);
            } else {
                Double codAmount = 0.0;

                for (DispatchTripDetail tripDetail: iteratingList){
                    boolean tripItemAdded = false;
                    if (tripDetail.getPickupDelivery().equals(PickupDeliveryEnum.Delivery.name()) &&
                            tripDetail.getDeliveryLocation().equals(detail.getDeliveryLocation())
                            && (tripDetail.getDeliveryLocationId()!= null && tripDetail.getDeliveryLocationId().equals(detail.getDeliveryLocationId()))){
                        giveShipmentList += tripDetail.getShipmentOrderId() + ",";
                        removeList.add(tripDetail);
                        tripItemAdded = true;
                    } else if (tripDetail.getPickupDelivery().equals(PickupDeliveryEnum.Pickup.name()) &&
                            tripDetail.getPickupLocation().equals(detail.getDeliveryLocation())
                            && (tripDetail.getPickupLocationId()!= null && tripDetail.getPickupLocationId().equals(detail.getPickupLocationId()))){
                        receiveShipmentList += tripDetail.getShipmentOrderId() + ",";
                        codAmount += tripDetail.getCodCollection();
                        removeList.add(tripDetail);
                        tripItemAdded = true;
                    } else if (tripDetail.getPickupDelivery().equals(PickupDeliveryEnum.Pickup.name()) &&
                            tripDetail.getPickupLocation().equals(detail.getPickupLocation())
                            && (tripDetail.getPickupLocationId()!= null && tripDetail.getPickupLocationId().equals(detail.getPickupLocationId()))){
                        receiveShipmentList += tripDetail.getShipmentOrderId() + ",";
                        codAmount += tripDetail.getCodCollection();
                        removeList.add(tripDetail);
                        tripItemAdded = true;
                    }

                    if (tripItemAdded && i < trip.getTripDetailItemsList().size()){
                        detail = trip.getTripDetailItemsList().get(i++);
                    }
                }

                if (task.getStopType() != null){

                    if((task.getStopType().equals(PickupTypeEnum.DealerPoint.getPickupTypeName()) || task.getStopType().equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName()))
                            && detail.getPickupDelivery().equals(PickupDeliveryEnum.Pickup.name()) ){
                        task.setLocationId(detail.getPickupLocationId());
                        task.setLocation(detail.getPickupLocation());
                    } else if((task.getStopType().equals(PickupTypeEnum.DealerPoint.getPickupTypeName()) || task.getStopType().equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName()))
                            && detail.getPickupDelivery().equals(PickupDeliveryEnum.Delivery.name()) ){
                        task.setLocationId(detail.getDeliveryLocationId());
                        task.setLocation(detail.getDeliveryLocation());
                    }
                    setLocationLatLongDetails(task);
                }

                task.setCodAmount(codAmount);
                task.setReceiveShipments(receiveShipmentList == null ? null: receiveShipmentList);
                task.setGiveShipments(giveShipmentList == null ? null: giveShipmentList);
                //removeList.add(detail);
            }

            if (removeList.size() > 0) {
                // Remove the items from iterating list for which task is created.
                iteratingList.removeAll(removeList);
                tasksList.add(task);
            }
        }

        sarokhTaskRepository.saveAll(tasksList);
        System.out.println(tasksList.size() + " Tasks created from Trip id = " + trip.getId());
    }

    private void setLocationLatLongDetails (SarokhTask task){
        if (task.getStopType().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())) {
            Optional<DealerPoint> point = dealerPointRepository.findById(task.getLocationId());
            if (point.isPresent()) {
                task.setLocationLatitude(point.get().getLocationLatitude());
                task.setLocationLongitude(point.get().getLocationLongitude());
            }
        } else if (task.getStopType().equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName())) {
            Optional<ShipperWarehouse> shipperWarehouse = shipperWarehouseRepository.findById(task.getLocationId());
            if (shipperWarehouse.isPresent()){
                task.setLocationLatitude(shipperWarehouse.get().getLocationLatitude());
                task.setLocationLongitude(shipperWarehouse.get().getLocationLongitude());
            }
        }
    }

    public Optional<DispatchTrip> getDispatchTripById(Integer id){
        Optional<DispatchTrip> trip = repository.findById(id);
        return trip;
    }

    public Iterable<DispatchTrip> getDispatchTripsList(){
        return repository.findAllDscOrder();
    }

    public ApiResponse deleteTrip(Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data("Trip Successfully deleted.")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getTripShipments(Integer warehouseId){

        Iterable<?> shipmentsList = orderItemRepository.findAllShipmentForCreateTrip(warehouseId);
        Iterator itr = shipmentsList.iterator();
        List<TripDetailsDTO>  deliveries = new ArrayList<>();
        List<TripDetailsDTO>  pickups = new ArrayList<>();

        while (itr.hasNext()){
            Object[] order = (Object[]) itr.next();
            String pickupType = order[1].toString();
            String pickupDelivery = PickupDeliveryEnum.Delivery.name();
            String deliveryType = null;

        // Need to change it into else if
            if (pickupType.equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName()) || pickupType.equals(PickupTypeEnum.DealerPoint.getPickupTypeName())){
                pickupDelivery = PickupDeliveryEnum.Pickup.name();

                // Sheduled once and picked up. Now schedule again for delivery.
                if (order[10] != null && order[10].toString().equals(ShipmentAssigneeEnum.Card.name()) ){
                    pickupDelivery = PickupDeliveryEnum.Delivery.name();
                }
            } else if (pickupType.equals(PickupTypeEnum.SarokhWarehouse.getPickupTypeName()) && (order[8] != null && order[8].equals(ShipmentAssigneeEnum.Card.name()) )){
                pickupDelivery = PickupDeliveryEnum.Delivery.name();
            }


            if (order[2] != null && (order[2].toString().equals(DeliveryTypeEnum.PredefinedLocation.getDeliveryTypeName()) || order[2].toString().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName()))){
                deliveryType = DeliveryTypeEnum.LastMile.getDeliveryTypeName();
            } else {
                deliveryType = DeliveryTypeEnum.SarokhPoint.getDeliveryTypeName();
            }

            Double codCollection = 0.0;
            if (order[6] != null){
                codCollection =  (Double) order[6];
            }

            Integer weight = 0;
            if (order[7] != null){
                String estimatedWeight = order[7].toString();
                if (estimatedWeight.equals("Upto 5 kg")) {
                    weight = 5;
                } else if (estimatedWeight.equals("5 kg to 10 kg")){
                    weight = 10;
                } else {
                    // "Above 15 kg"
                    weight = 15;
                }
            }

            String address = order[4] != null ? order[4].toString() : null;

            if (pickupDelivery.equals(PickupDeliveryEnum.Pickup.name())){
                address = getDestinationAddress(order[3] != null ? order[3].toString() : null, order[11] != null ? (Integer) order[11] : null);
            } else if (pickupDelivery.equals(PickupDeliveryEnum.Delivery.name())){
                if (order[2] != null && !order[2].toString().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())) {
                    address = getDestinationAddress(order[2] != null ? order[2].toString() : null, order[8] != null ? (Integer) order[8] : null);
                }
            }

            if (pickupDelivery.equals(PickupDeliveryEnum.Pickup.name())){
                pickups.add(TripDetailsDTO.builder()
                        .shipmentId(order[0].toString())
                        .warehouseId(warehouseId)
                        .deliveryPickup(pickupDelivery)
                        .locationName(order[3] != null ? order[3].toString() : null)
                        .pickupType(pickupType)
                        .address(address)
                        .CODCollection(codCollection)
                        .weight(weight)
                        .shipper(order[12] + " " + order[13])
                        .build());
            } else {
                deliveries.add(TripDetailsDTO.builder()
                        .shipmentId(order[0].toString())
                        .warehouseId(warehouseId)
                        .deliveryPickup(pickupDelivery)
                        .locationName(order[9] != null ? order[9].toString() : order[2].toString())
                        .deliveryType(deliveryType)
                        .address(address)
                        .CODCollection(codCollection)
                        .weight(weight)
                        .shipper(order[12] + " " + order[13])
                        .build());
            }
        }

        return ApiResponse.builder()
                .data(CreateTripSearchResultsDTO.builder()
                        .deliveries(deliveries)
                        .pickups(pickups)
                        .pointsList(dealerPointService.getDealerPointCollectionWallets())
                        .build())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    private String getDestinationAddress(String locationType, Integer locationId){
        String address = null;

        if (locationType != null && locationId != null){
            if (locationType.equals(PickupTypeEnum.DealerPoint.getPickupTypeName()) || locationType.equals(DeliveryTypeEnum.SarokhPoint.getDeliveryTypeName())){
                Optional<DealerPoint> location = dealerPointRepository.findByDealerId(locationId);
                address = location.isPresent() ? location.get().getAddress() : null;
            } else if (locationType.equals(PickupTypeEnum.SarokhWarehouse.getPickupTypeName())){
                Optional<SarokhWarehouse> location = sarokhWarehouseRepository.findById(locationId);
                address = location.isPresent() ? location.get().getAddress() : null;
            } else if (locationType.equals(PickupTypeEnum.ShipperWarehouse.getPickupTypeName())){
                Optional<ShipperWarehouse> location = shipperWarehouseRepository.findById(locationId);
                address = location.isPresent() ? location.get().getAddress() : null;
            }
        }

        return address;
    }

    public ApiResponse getAllTripsForAdmin(){

        Iterable<DispatchTrip> trips = repository.findAllDscOrder();
        Iterator<DispatchTrip> itr = trips.iterator();

        List<TripListingDTO> list = new ArrayList<>();

        while (itr.hasNext()){
            DispatchTrip trip = itr.next();
            Optional<Driver> driver = driverRepository.findById(trip.getDriverId());

            DateTimeDTO dateTimeDTO = trip.getDispatchDatetime() != null ? DateUtil.convertToDateAndTime(DateUtil.toDateFromSQLString(trip.getDispatchDatetime().toString())) : null;
            list.add(TripListingDTO.builder()
                    .tripId(trip.getId())
                    .date(dateTimeDTO != null ? dateTimeDTO.getDate() : null)
                    .sarkhWarehouse(trip.getStartPoint())
                    .driver(driver.get().getFirstName() + " " + driver.get().getLastName())
                    .driverType(driver.get().getDriverType())
                    .shipmentPickup(trip.getPickupShipments())
                    .shipmentDelivered(trip.getDeliveryShipments())
                    .CODCollection(trip.getCodCollection())
                    .status(trip.getTripStatus())
                    .build());
        }

        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getActiveTripsForAdmin(){
        Iterable<DispatchTrip> trips = repository.findAllByTripStatus("Active");
        Iterator<DispatchTrip> itr = trips.iterator();

        List<TripListingDTO> list = new ArrayList<>();

        while (itr.hasNext()){
            DispatchTrip trip = itr.next();
            Optional<Driver> driver = driverRepository.findById(trip.getDriverId());

            list.add(TripListingDTO.builder()
                    .tripId(trip.getId())
                    .date(trip.getDispatchDatetime())
                    .sarkhWarehouse(trip.getStartPoint())
                    .driver(driver.get().getFirstName() + " " + driver.get().getLastName())
                    .driverType(driver.get().getDriverType())
                    .shipmentPickup(trip.getPickupShipments())
                    .shipmentDelivered(trip.getDeliveryShipments())
                    .CODCollection(trip.getCodCollection())
                    .status(trip.getTripStatus())
                    .build());
        }


        return ApiResponse.builder()
                .data(list)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public boolean isDriverHasActiveTrips(Integer driverId){
        List<DispatchTrip> trips = repository.findAllByDriverIdAndTripStatus(driverId, "Active");

        return trips != null && trips.size() > 0;
    }

}
