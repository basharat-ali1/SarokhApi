package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "dispatch_trip", schema = "sarokh_db")
public class DispatchTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "start_point", nullable = true)
    private String startPoint;

    @Basic
    @Column(name = "end_point", nullable = true)
    private String endPoint;

    @Basic
    @Column(name = "dispatch_datetime", nullable = true)
    private Date dispatchDatetime;

    @Basic
    @Column(name = "driver_compensation", nullable = true)
    private Double driverCompensation;

    @Basic
    @Column(name = "trip_completion_datetime", nullable = true)
    private Date tripCompletionDatetime;

    @Basic
    @Column(name = "status", nullable = false)
    private String tripStatus;

    @Basic
    @Column(name = "driver_id", nullable = false)
    private Integer driverId;

    @Basic
    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Basic
    @Column(name = "pickup_shipments", nullable = false)
    private Integer pickupShipments;

    @Basic
    @Column(name = "delivery_shipments", nullable = false)
    private Integer deliveryShipments;

    @Basic
    @Column(name = "cod_collection", nullable = false)
    private Double codCollection;

    @Basic
    @Column(name = "vehicle_id", nullable = false)
    private Integer vehicleId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vehicle_id", insertable=false, updatable=false)
    private Vehicle vehicle;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "dispatch_trip_id")
    private List<DispatchTripDetail> tripDetailItemsList;

}
