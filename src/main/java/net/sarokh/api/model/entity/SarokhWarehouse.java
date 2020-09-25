package net.sarokh.api.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Sarokh_Warehouse", schema = "Sarokh_db")
public class SarokhWarehouse {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "postal_code", nullable = true)
    private Integer postalCode;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "operational_time", nullable = true)
    private String operationalTime;

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

    @Column(name = "dimensions", nullable = true)
    private String dimensions;

    @Basic
    @Column(name = "rows", nullable = true)
    private Integer rows;

    @Basic
    @Column(name = "columns_per_row", nullable = true)
    private Integer columnsPerRow;

    @Basic
    @Column(name = "racks_per_row", nullable = true)
    private Integer racksPerRow;

    @Column(name = "location_latitude", nullable = true)
    private String locationLatitude;

    @Column(name = "location_longitude", nullable = true)
    private String locationLongitude;

}
