package net.sarokh.api.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "dispatch_trip_details", schema = "sarokh_db")
public class DispatchTripDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "shipment_order_id", nullable = true)
    private String shipmentOrderId;

    @Basic
    @Column(name = "pickup_location", nullable = true)
    private String pickupLocation;

    @Basic
    @Column(name = "pickup_location_id", nullable = true)
    private Integer pickupLocationId;

    @Basic
    @Column(name = "delivery_location", nullable = true)
    private String deliveryLocation;

    @Basic
    @Column(name = "delivery_location_id", nullable = true)
    private Integer deliveryLocationId;

    @Basic
    @Column(name = "pickup_delivery", nullable = true)
    private String pickupDelivery;

    @Basic
    @Column(name = "address", nullable = true)
    private String address;

    @Basic
    @Column(name = "payment_type", nullable = true)
    private String paymentType;

    @Basic
    @Column(name = "cod_collection", nullable = true)
    private Double codCollection;

    @Basic
    @Column(name = "dispatch_trip_id", nullable = false)
    private Integer dispatchTripId;

    @Basic
    @Column(name = "location_latitude", nullable = false)
    private String locationLatitude;

    @Basic
    @Column(name = "location_longitude", nullable = false)
    private String locationLongitude;

    @Column(name = "stop_type", nullable = true)
    private String stopType;

}
