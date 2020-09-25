package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Shipper_Warehouse", schema = "Sarokh_db")
public class ShipperWarehouse {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "name", nullable = true)
    private String name;

    @Basic
    @Column(name = "address", nullable = true)
    private String address;

    @Basic
    @Column(name = "city", nullable = true)
    private String city;

    @Basic
    @Column(name = "country", nullable = true)
    private String country;

    @Basic
    @Column(name = "postal_code", nullable = true)
    private Integer postalCode;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @Column(name = "manager_contact", nullable = false)
    private String mangerContact;

    @Column(name = "manager_email", nullable = false)
    private String mangerEmail;

    @Basic
    @Column(name = "fork_lifter", nullable = true)
    private Boolean forkLifter;

    @Basic
    @Column(name = "qr_scanner", nullable = true)
    private Boolean QRScanner;

    @Basic
    @Column(name = "thermal_printer", nullable = true)
    private Boolean thermalPrinter;

    @Column(name = "operational_time_from", nullable = true)
    private String operationalTimefrom;

    @Column(name = "operational_time_to", nullable = true)
    private String operationalTimeto;

    @Basic
    @Column(name = "location_longitude", nullable = true)
    private String locationLongitude;

    @Basic
    @Column(name = "location_latitude", nullable = true)
    private String locationLatitude;

    @Basic
    @Column(name = "shipper_id", nullable = false)
    private int shipperId;

    @Basic
    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = true)
    private Date updatedDatetime;

    @Basic
    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;



}
