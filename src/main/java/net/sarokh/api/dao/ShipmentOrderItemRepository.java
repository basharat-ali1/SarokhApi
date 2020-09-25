package net.sarokh.api.dao;

import net.sarokh.api.model.entity.ShipmentOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ShipmentOrderItemRepository extends JpaRepository<ShipmentOrderItem, Integer> {

    Iterable<ShipmentOrderItem> findAllByShipmentOrderId(Integer id);

    Optional<ShipmentOrderItem> findByTrackingNumber(String trackingNumber);

    //Iterable<ShipmentOrderItem> findAllByAssignToIdAndPaymentType(Integer assignToId, String paymentType);

    //Iterable<ShipmentOrderItem> findAllByGiveShipmentTo(Integer giveShipmentToId);

    @Query(value = "SELECT * FROM shipment_order_item where shipment_order_id IN (SELECT id FROM shipment_order WHERE shipper_id = :shipperId)", nativeQuery = true)
    List<ShipmentOrderItem> findAllShipmentsByShipperId(@Param("shipperId") Integer shipperId);

    @Query(value = "SELECT  shipmentItem.payment_type, shipmentItem.delivery_status, COUNT(shipmentItem.delivery_status) As 'number_of_orders' " +
            "FROM shipment_order_item shipmentItem where shipmentItem.shipment_order_id IN (SELECT sorder.id FROM shipment_order sorder WHERE sorder.shipper_id = :shipperId) " +
            "GROUP BY shipmentItem.delivery_status, shipmentItem.payment_type  ORDER BY shipmentItem.id DESC", nativeQuery = true)
    Iterable<?> findAllByDeliveryStatusAndPaymentType(@Param("shipperId") Integer shipperId);

    @Query(value = "SELECT * FROM shipment_order_item where shipment_order_id IN (SELECT id FROM shipment_order WHERE shipper_id = :shipperId) " +
            "AND delivery_status = :deliveryStatus ORDER BY id DESC", nativeQuery = true)
    List<ShipmentOrderItem> findAllByDeliveryStatusAndShipperId(String deliveryStatus, Integer shipperId);

    @Query(value = "SELECT orderitem.id, orderitem.tracking_number, orderitem.shipment_title, orderitem.delivery_status, orderitem.payment_type, orderitem.shipment_billed_amount " +
            "FROM shipment_order_item orderitem, shipment_order ord where orderitem.shipment_order_id=ord.id  AND payment_type = 'COD' AND ledger_created=0 AND " +
            "(ord.created_datetime >=:startDate AND ord.created_datetime <=:endDate) AND ord.shipper_id =:shipperId ORDER BY orderitem.id DESC", nativeQuery = true)
    List<?> findAllByDateAndPaymentType(String startDate, String endDate, Integer shipperId);

    @Query(value = "SELECT orderitem.id, orderitem.tracking_number, ord.created_datetime, orderitem.cod_amount, orderitem.payment_type " +
            "FROM shipment_order_item orderitem, shipment_order ord where orderitem.shipment_order_id=ord.id  AND payment_type = 'COD' AND ledger_created=0 AND " +
            "(ord.created_datetime >=:startDate AND ord.created_datetime <=:endDate) ORDER BY orderitem.id DESC", nativeQuery = true)
    List<?> findAllByDateAndPaymentType(String startDate, String endDate);

    @Query(value = "SELECT  shipment.order_id, shipment.created_datetime, shipment.ship_from_city, shipment.ship_to_city, shipment.status, "+
            "shipper.first_name, shipper.last_name, shipment.assign_to, shipment.assign_to_id, shipment.assign_to_detail, shipment.id "+
            "FROM shipment_order shipment, shipper shipper  where shipment.shipper_id = shipper.id ORDER BY shipment.id DESC", nativeQuery = true)
    Iterable<?> findAllShipments();

    @Query(value = "SELECT  shipmentItem.tracking_number, shipment.created_datetime, shipmentItem.shipment_type, "+
            "shipmentItem.shipment_billed_amount As 'amount', shipper.first_name, shipper.last_name, shipmentItem.delivery_status, " +
            "shipmentItem.payment_type, shipment.pickup_location, shipment.pickup_location_detail, shipment.id AS 'shipmentId', " +
            "shipment.assign_to, shipment.assign_to_id, shipment.assign_to_detail, shipment.ship_from_city, shipment.ship_to_city, shipment.delivery_location "+
            "FROM shipment_order shipment, shipment_order_item shipmentItem, shipper shipper where shipment.shipper_id = shipper.id "+
            "AND shipmentItem.shipment_order_id = shipment.id AND shipmentItem.delivery_status = :status ORDER BY shipmentItem.id DESC", nativeQuery = true)
    Iterable<?> findAllShipmentsByDeliveryStatus(String status);

    @Query(value = "SELECT  shipmentItem.tracking_number, shipment.created_datetime, shipmentItem.shipment_type, "+
            "shipmentItem.shipment_billed_amount As 'amount', shipper.first_name, shipper.last_name, shipmentItem.delivery_status, "+
            "shipmentItem.payment_type, shipment.pickup_location, shipment.pickup_location_detail, shipment.id AS 'shipmentId', "+
            "shipment.assign_to, shipment.assign_to_id, shipment.assign_to_detail, shipment.ship_from_city, shipment.ship_to_city, shipment.delivery_location "+
            "FROM shipment_order shipment, shipment_order_item shipmentItem, shipper shipper where shipment.shipper_id = shipper.id "+
            "AND shipmentItem.shipment_order_id = shipment.id AND (shipmentItem.delivery_status = 'Missing' OR shipmentItem.delivery_status = 'Stolen') ORDER BY shipmentItem.id DESC", nativeQuery = true)
    Iterable<?> findAllShipmentsWithIssues();

    @Query(value = "SELECT  shipment.order_id, shipment.created_datetime, shipment.ship_from_city, shipment.ship_to_city, shipment.status,  "+
            "shipper.first_name, shipper.last_name, shipmentItem.cod_amount, shipment.id "+
            "FROM shipment_order shipment, shipment_order_item shipmentItem, shipper shipper where shipment.shipper_id = shipper.id "+
            "AND shipmentItem.shipment_order_id = shipment.id AND shipmentItem.payment_type = :paymentType ORDER BY shipmentItem.id DESC", nativeQuery = true)
    Iterable<?> findAllShipmentsByPaymentType(String paymentType);

    @Query(value = "SELECT  shipmentItem.tracking_number, shipment.created_datetime, shipmentItem.shipment_type, "+
            "shipmentItem.shipment_billed_amount As 'amount', shipper.first_name, shipper.last_name, shipmentItem.delivery_status, "+
            "shipmentItem.payment_type, shipment.pickup_location, shipment.pickup_location_detail "+
            "FROM shipment_order shipment, shipment_order_item shipmentItem, shipper shipper where shipment.shipper_id = shipper.id "+
            "AND shipmentItem.shipment_order_id = shipment.id AND shipmentItem.delivery_status = :status", nativeQuery = true)
    Iterable<?> findAllShipmentsByShipmentStatus(String status);

    @Query(value = "SELECT tripdetail.shipment_order_id, trip.dispatch_datetime, shipment.delivery_location_detail, shipment.status, driver.first_name, " +
            "driver.last_name, shipment.id as 'shipmentId' FROM dispatch_trip trip, dispatch_trip_details tripdetail, shipment_order shipment, driver driver " +
            "WHERE trip.id = tripdetail.dispatch_trip_id AND tripdetail.shipment_order_id = shipment.order_id AND driver.id = trip.driver_id " +
            "AND trip.status = 'Active'AND tripdetail.pickup_delivery = :pickupDelivery ORDER BY trip.id DESC", nativeQuery = true)
    Iterable<?> findShipmentsByDeliveryOrPickup(String pickupDelivery);

    @Query(value = "SELECT  shipmentItem.tracking_number, shipment.created_datetime, shipmentItem.shipment_type, "+
            "shipmentItem.shipment_billed_amount As 'amount', shipper.first_name, shipper.last_name, shipmentItem.delivery_status "+
            "FROM shipment_order shipment, shipment_order_item shipmentItem, shipper shipper where shipment.shipper_id = shipper.id "+
            "AND shipmentItem.shipment_order_id = shipment.id ", nativeQuery = true)
    Iterable<?> findDealerInventory();

    @Query(value = "SELECT shipment.tracking_number,  shipment.id, report.reported_by, shipment.delivery_status, report.complaint_description, ord.id AS 'shipmentId', ord.ship_from_city, ord.ship_to_city " +
            "FROM shipment_order AS ord, shipment_order_item AS shipment, shipment_issue_report AS report WHERE shipment.report_issue_id = report.id " +
            "AND shipment.shipment_order_id = ord.id AND ord.shipper_id=:shipperId AND (shipment.delivery_status='Missing' OR shipment.delivery_status='Stolen') ORDER BY id DESC"
            , nativeQuery = true)
    Iterable<?> findAllShipmentsIssuesByShipperId(@Param("shipperId") Integer shipperId);

    @Query(value = "SELECT distinct shipper.id, shipper.first_name, shipper.last_name FROM shipment_order AS ord, " +
            "shipment_order_item AS shipment, shipper AS shipper WHERE shipment.shipment_order_id = ord.id " +
            "AND shipment.delivery_status = 'Pending'"
            , nativeQuery = true)
    Iterable<?> findAllShippersWithPendingOrders();

    @Query(value = "SELECT shipment.tracking_number FROM shipment_order AS ord, shipment_order_item AS shipment, " +
            "shipper AS shipper WHERE shipment.shipment_order_id = ord.id AND shipper.id = :shipperId " +
            "AND shipment.delivery_status != 'Delivered'"
            , nativeQuery = true)
    Iterable<?> findAllPendingOrdersByShipperId(Integer shipperId);

    @Query(value = "SELECT ord.order_id, ord.pickup_location, ord.delivery_location,ord.pickup_location_detail, " +
            "shipment.address, shipment.payment_type , shipment.cod_amount, shipment.weight, ord.delivery_location_id, " +
            "ord.delivery_location_detail, ord.assign_to, ord.pickup_location_id, shipper.first_name, shipper.last_name  " +
            "FROM shipment_order AS ord, shipment_order_item AS shipment, shipper As shipper " +
            "WHERE  (shipment.shipment_order_id = ord.id  AND shipper.id=ord.shipper_id) AND (shipment.delivery_status = 'Pending' OR ord.status='InProcess') " +
            "AND (ord.dispatch_trip_id  IS NULL OR ord.dispatch_trip_id = 0) AND ((ord.assign_to = 'Card' AND ord.assign_to_id =:warehouseId) " +
            "OR (ord.pickup_location = 'Sarokh Warehouse' AND ord.pickup_location_id =:warehouseId AND ord.assign_to = 'Card') " +
            "OR (ord.ship_from_city = (SELECT city FROM sarokh_warehouse where id =:warehouseId) AND ord.pickup_location = 'Shipper Warehouse') " +
            "OR (ord.ship_from_city = (SELECT city FROM sarokh_warehouse where id =:warehouseId) AND ord.pickup_location = 'Sarokh Point') " +
            "OR (ord.pickup_location = 'Sarokh Point' AND ord.assign_to = 'Sarokh Point'))" , nativeQuery = true)
    Iterable<?> findAllShipmentForCreateTrip(Integer warehouseId);

}



