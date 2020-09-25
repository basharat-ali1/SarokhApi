package net.sarokh.api.service;

import net.sarokh.api.dao.SarokhTaskRepository;
import net.sarokh.api.model.entity.DispatchTrip;
import net.sarokh.api.model.entity.DispatchTripDetail;
import net.sarokh.api.model.enums.PickupDeliveryEnum;
import net.sarokh.api.model.entity.SarokhTask;
import net.sarokh.api.model.enums.DeliveryTypeEnum;
import net.sarokh.api.model.enums.DispatchTripStatusEnum;
import net.sarokh.api.model.enums.PaymentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SarokhTaskService {

    @Autowired
    private SarokhTaskRepository taskRepository;

    public void createSarokhTasksFromTrip(DispatchTrip trip){
        Map<Integer, SarokhTask> map = new HashMap<>();

        List<DispatchTripDetail> tripDetailList = trip.getTripDetailItemsList();

        SarokhTask task = null;
        for (DispatchTripDetail tripDetail:tripDetailList){

            if (tripDetail.getDeliveryLocation().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())){
                task = new SarokhTask();
                task.setDriverId(trip.getDriverId());
                task.setDriverName(trip.getDriverName());
                task.setTripId(trip.getId());
                task.setCodAmount(tripDetail.getCodCollection());
                task.setStatus(DispatchTripStatusEnum.Active.name());
                task.setLocation(DeliveryTypeEnum.LastMile.getDeliveryTypeName());
                task.setAddress(tripDetail.getAddress());
                if (tripDetail.getPaymentType().equals(PaymentTypeEnum.COD.name())){
                    task.setCodAmount(tripDetail.getCodCollection());
                }
                // Lat and long

            } else if (null == map.get(tripDetail.getDeliveryLocationId())){
                task = new SarokhTask();
                task.setDriverId(trip.getDriverId());
                task.setDriverName(trip.getDriverName());
                task.setTripId(trip.getId());
                task.setCodAmount(tripDetail.getCodCollection());
                task.setStatus(DispatchTripStatusEnum.Active.name());

                if (tripDetail.getPickupDelivery().equals(PickupDeliveryEnum.Pickup.name())){
                    task.setGiveShipments(tripDetail.getShipmentOrderId());
                } else if (tripDetail.getPickupDelivery().equals(PickupDeliveryEnum.Delivery.name())){
                    task.setReceiveShipments(tripDetail.getShipmentOrderId());
                }

                map.put(tripDetail.getDeliveryLocationId(), task);
            } else {
                task = map.get(tripDetail.getDeliveryLocationId());
                task.setCodAmount(task.getCodAmount() + tripDetail.getCodCollection());

                if( task.getGiveShipments() != null && task.getGiveShipments().length() > 0 && tripDetail.getPickupDelivery().equals(PickupDeliveryEnum.Delivery.name())) {
                    task.setGiveShipments(task.getGiveShipments() + "," + tripDetail.getShipmentOrderId());
                } else {
                    task.setGiveShipments(tripDetail.getShipmentOrderId());
                }

                if( task.getReceiveShipments() != null && task.getReceiveShipments().length() > 0 && tripDetail.getPickupDelivery().equals(PickupDeliveryEnum.Pickup.name()) ) {
                    task.setReceiveShipments(task.getReceiveShipments() + "," + tripDetail.getShipmentOrderId());
                } else {
                    task.setReceiveShipments(tripDetail.getShipmentOrderId());
                }

                map.put(tripDetail.getDeliveryLocationId(), task);
            }
        }

    }

    public SarokhTask getSarokhTaskForDealerPoint(Integer pointId){
        return null;
    }

    public Iterator<SarokhTask> getSarokhTasksForDriver(Integer driverId){
        return null;
    }

    public SarokhTask completeSarokhTask(Integer pointId, Integer driverId){
        return null;
    }



}
