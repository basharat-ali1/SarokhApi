package net.sarokh.api.dao;

import net.sarokh.api.model.Order;
import net.sarokh.api.model.entity.Role;
import net.sarokh.api.model.entity.ShipmentOrder;
import net.sarokh.api.model.entity.ShipmentOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<ShipmentOrder, Integer> {

    //public Iterable<ShipmentOrder> findShipmentOrderByOrderType(String type);

    Optional<ShipmentOrder> findByOrderId(String orderId);

    @Query(value = "SELECT * FROM `shipment_order`  where order_id >=:startTrackingNumber AND order_id<=:endTrackingNumber", nativeQuery = true)
    List<ShipmentOrder> findOrdersNumbersInRange(String startTrackingNumber, String endTrackingNumber);

    @Query(value = "SELECT * FROM `shipment_order` ORDER BY id DESC", nativeQuery = true)
    List<ShipmentOrder> findAllDescOrder();

    @Query(value = "SELECT MAX(order_id) FROM `shipment_order` WHERE shipper_id =:shipperId", nativeQuery = true)
    Object getMaxOrderIdByShipperId(Integer shipperId);

    @Query(value = "SELECT * FROM `shipment_order` WHERE shipper_id =:shipperId ORDER BY id DESC", nativeQuery = true)
    List<ShipmentOrder> findAllByShipperId(Integer shipperId);

    List<ShipmentOrder> findAllByAssignToAndAssignToId(String assignTo, Integer assignToId);

    List<ShipmentOrder> findByStatusAndShipperId(String status, Integer shipperId);

    @Query(value = "SELECT ord.order_id, ord.created_datetime, ord.pickup_location, ord.delivery_location, shipment.receiver_name, shipment.delivery_status " +
            "FROM shipment_order AS ord, shipment_order_item AS shipment " +
            "WHERE shipment.shipment_order_id = ord.id AND ord.shipper_id=:shipperId ORDER BY id DESC", nativeQuery = true)
    Iterable<?> findAllShipmentsByShipperId(@Param("shipperId") Integer shipperId);

    @Query(value = "SELECT ord.order_id, ord.created_datetime, shipment.delivery_status, shipment.receiver_name, shipment.payment_type, " +
            "shipment.cod_amount, ord.id, ord.ship_from_city, ord.ship_to_city, shipment.shipment_title " +
            "FROM shipment_order AS ord, shipment_order_item AS shipment WHERE shipment.shipment_order_id = ord.id " +
            "AND ord.shipper_id=:shipperId AND shipment.delivery_status='Pending' ORDER BY id DESC" , nativeQuery = true)
    Iterable<?> findAllPendingShipmentsByShipperId(@Param("shipperId") Integer shipperId);

    @Query(value = "SELECT order_id, transit_location_longitude, transit_location_latitude FROM shipment_order " +
            "WHERE status='Pending' OR status='InProcess' OR status='Active'" , nativeQuery = true)
    Iterable<?> findAllOrdersLocations();

    @Query(value = "SELECT ord.order_id, ord.created_datetime, shipment.receiver_name, shipment.cod_amount, shipment.delivery_status, ord.id, ord.ship_from_city, ord.ship_to_city " +
            "FROM shipment_order AS ord, shipment_order_item AS shipment WHERE shipment.shipment_order_id = ord.id " +
            "AND ord.shipper_id=:shipperId AND shipment.payment_type='COD' ORDER BY id DESC" , nativeQuery = true)
    Iterable<?> findCODShipmentsByShipperId(@Param("shipperId") Integer shipperId);

    @Query(value = "SELECT ord.order_id, ord.pickup_location, ord.pickup_location_id, ord.delivery_location, ord.delivery_location_id, shipment.payment_type, " +
            "shipment.cod_amount, ord.dispatch_trip_id, shipment.receiver_name, shipment.shipment_billed_amount, ord.assign_to, shipment.delivery_date, ord.shipper_id " +
            "FROM shipment_order AS ord, shipment_order_item AS shipment WHERE shipment.shipment_order_id = ord.id " +
            "AND shipment.delivery_status = 'Pending' AND ( (ord.pickup_location='Sarokh Point' AND ord.pickup_location_id =:dealerId ) " +
            "OR ( ord.delivery_location = 'To Sarokh Point' AND ord.delivery_location_id =:dealerId ) ) AND ord.dispatch_trip_id IS NOT NULL" , nativeQuery = true)
    Iterable<?> getDealerSarokhTask(@Param("dealerId") Integer dealerId);

    /*@Query(value = "SELECT ord.order_id, ord.shipper_id FROM shipment_order AS ord, dispatch_trip AS trip, " +
            "dispatch_trip_details AS tripdetail WHERE tripdetail.shipment_order_id=ord.order_id AND " +
            "trip.id = tripdetail.dispatch_trip_id AND trip.status = 'Active' AND ord.shipper_id = :shipperId" , nativeQuery = true)*/
    @Query(value = "SELECT ord.order_id, ord.pickup_location, ord.pickup_location_detail FROM shipment_order AS ord " +
            "WHERE (pickup_location ='Shipper Warehouse' OR pickup_location ='Sarokh Point') AND shipper_id =:shipperId AND " +
            " (ord.status = 'Active' OR ord.status = 'Pending')" , nativeQuery = true)
    Iterable<?> getDealerShipperReceiveList(@Param("shipperId") Integer shipperId);


    @Query(value = "SELECT SUM(CASE WHEN shipment_detail.payment_type ='COD' THEN shipment_detail.cod_amount END) As 'Total Earning',  " +
            "SUM(CASE WHEN shipment_detail.delivery_status='Delivered' THEN 1 END) AS 'Completed Orders'," +
            "SUM(CASE WHEN shipment_detail.delivery_status='Pending' THEN (shipment_detail.shipment_billed_amount-shipment_detail.shipment_delivery_charges) END) AS 'Pending Receiveable'," +
            "COUNT(shipment_detail.tracking_number ) As 'Total Orders', " +
            "SUM(CASE WHEN shipment_detail.delivery_status='Pending' THEN 1 END) AS 'Pending orders', " +
            "SUM(CASE WHEN shipment_detail.delivery_status='Returned' THEN 1 END) AS 'Returned', " +
            "SUM(CASE WHEN shipment_detail.delivery_status='Missing' THEN 1 END) AS 'Shipment Issues' " +
            "FROM shipment_order_item shipment_detail, shipment_order shipment " +
            "WHERE shipment_detail.tracking_number = shipment.order_id AND shipment.shipper_id =:shipperId", nativeQuery = true)
    Iterable<?> loadShipperDashboard(Integer shipperId);
}
