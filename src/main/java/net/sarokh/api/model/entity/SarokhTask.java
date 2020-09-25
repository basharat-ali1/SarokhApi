package net.sarokh.api.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sarokh_task", schema = "sarokh_db")
public class SarokhTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dispatch_trip_id", nullable = true)
    private Integer tripId;

    @Column(name = "location_id", nullable = true)
    private Integer locationId;

    @Column(name = "location_name", nullable = true)
    private String location;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "driver_id", nullable = true)
    private Integer driverId;

    @Column(name = "driver_name", nullable = true)
    private String driverName;

    @Column(name = "receive_shipments", nullable = true)
    private String receiveShipments;

    @Column(name = "give_shipments", nullable = true)
    private String giveShipments;

    @Column(name = "location_latitude", nullable = true)
    private String locationLatitude;

    @Column(name = "location_longitude", nullable = true)
    private String locationLongitude;

    @Column(name = "cod_amount", nullable = true)
    private Double codAmount;

    @Column(name = "driver_confirmation", nullable = true)
    private Boolean confirmedByDriver;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "stop_type", nullable = true)
    private String stopType;

    @Column(name = "amount_received", nullable = true)
    private Boolean amountReceived;
}
