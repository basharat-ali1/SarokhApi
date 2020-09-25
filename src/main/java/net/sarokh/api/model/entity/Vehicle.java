package net.sarokh.api.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Vehicle", schema = "Sarokh_db")
public class Vehicle {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "name", nullable = true)
    private String name;

    @Basic
    @Column(name = "owner", nullable = true)
    private String owner;

    @Basic
    @Column(name = "registration_number", nullable = true)
    private String registrationNumber;

    @Basic
    @Column(name = "registration_file", nullable = true)
    private String registrationFile;

    @Basic
    @Column(name = "model", nullable = true)
    private String model;

    @Basic
    @Column(name = "make", nullable = true)
    private String make;

    @Basic
    @Column(name = "registration_year", nullable = true)
    private Integer registrationYear;

    @Basic
    @Column(name = "production_year", nullable = true)
    private Integer productionYear;

    @Basic
    @Column(name = "type", nullable = true)
    private String type;

    @Basic
    @Column(name = "cargo_capacity", nullable = true)
    private String cargoCapacity;

    @Basic
    @Column(name = "current_milage", nullable = true)
    private Integer currentMileage;

    @Basic
    @Column(name = "status", nullable = true)
    private String status;

    @Basic
    @Column(name = "warehouse_id", nullable = true)
    private String warehouseId;

    @Basic
    @CreationTimestamp
    @Column(name = "created_date", nullable = true)
    private Date createdDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id", insertable=false, updatable=false)
    private SarokhWarehouse warehouse;

}
