package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "Shipment_Order", schema = "Sarokh_db")
public class ShipmentOrder {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // Basic Info
        @Column(name = "order_id", nullable = false)
        private String orderId;

        @Column(name = "shipper_id", nullable = false)
        private int shipperId;

    //  From and Pickup Details

        @Column(name = "ship_from_city", nullable = false)
        private String shipFromCity;

        @Column(name = "pickup_location", nullable = false)
        private String pickupLocation;

        @Column(name = "pickup_location_id", nullable = true)
        private Integer pickupLocationId;

        @Column(name = "pickup_location_detail", nullable = true)
        private String pickupLocationDetail;

    //  To and Delivery Details

        @Column(name = "ship_to_city", nullable = false)
        private String shipToCity;

        @Column(name = "delivery_location", nullable = false)
        private String deliveryLocation;

        @Column(name = "delivery_location_id", nullable = true)
        private Integer deliveryLocationId;

        @Column(name = "delivery_location_detail", nullable = true)
        private String deliveryLocationDetail;

    // Assignee Details

        @Column(name = "assign_to", nullable = true)
        private String assignTo;

        @Column(name = "assign_to_id", nullable = true)
        private Integer assignToId;

        @Column(name = "assign_to_detail", nullable = true)
        private String assignToDetail;

    // General

    @Column(name = "dispatch_trip_id", nullable = true)
    private Integer dispatchTripId;

    @Column(name = "pickup_datetime", nullable = true)
    private Date pickupDatetime;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "receiver_type", nullable = true)
    private String receiverType;

    @Column(name = "transit_location_longitude", nullable = true)
    private String transitLocationLongitude;

    @Column(name = "transit_location_latitude", nullable = true)
    private String transitLocationLatitude;

    @CreationTimestamp
    @Column(name = "created_datetime", nullable = false)
    private Date createdDatetime;

    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = false)
    private Date updatedDatetime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_order_id")
    private List<ShipmentOrderItem> shipmentOrderItems;

}
